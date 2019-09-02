package de.niku.braincards.view.fragment_card_sets

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanniktech.rxpermission.Permission
import com.vanniktech.rxpermission.RealRxPermission
import de.niku.braincards.BR
import de.niku.braincards.Constants

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.common.dialogs.DecisionDialog
import de.niku.braincards.common.dialogs.InfoDialog
import de.niku.braincards.databinding.FragmentCardSetsBinding
import de.niku.braincards.util.colorMenuItem
import de.niku.braincards.util.getResColorInt
import de.niku.braincards.view.activity_main.MainActivity

class CardSetsFragment : BaseFragment<FragmentCardSetsBinding, CardSetsViewModel>(),
    ActionMode.Callback {

    lateinit var cardSetAdapter: CardSetAdapter
    lateinit var actionMode: ActionMode

    var writeExtStoragePermGranted: Boolean = false
    var readExtStoragePermGranted: Boolean = false

    override fun getLayoutResId(): Int = R.layout.fragment_card_sets
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
        mDataBinding.setVariable(BR.viewstate, mViewModel.vdViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        connectObservables()

        if (!this::cardSetAdapter.isInitialized) {
            cardSetAdapter = CardSetAdapter(mViewModel, context)
        }

        mDataBinding.rvCardSets.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvCardSets.adapter = cardSetAdapter

        mViewModel.fetchCardSets()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.card_sets_menu, menu)
        colorMenuItem(context!!, menu, getResColorInt(context!!, R.color.white))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_import -> {
                Toast.makeText(context, "Import Data", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_export -> {
                if (activity is MainActivity) {
                    (activity as MainActivity)?.lockNavDrawer()
                }
                actionMode = activity?.startActionMode(this)!!
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_export -> {
                checkSorageIOPermissions()
                return true
            }
            R.id.menu_select_all -> {
                cardSetAdapter.selectAll()
                return true
            }
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        var inflater: MenuInflater = activity!!.menuInflater
        inflater.inflate(R.menu.card_sets_cab_menu, menu)
        mDataBinding.fab.hide()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode!!.setTitle("Select Items")
        cardSetAdapter.startSelectionMode(mode)
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        if (activity is MainActivity) {
            (activity as MainActivity)?.unlockNavDrawer()
        }
        mDataBinding.fab.show()
        cardSetAdapter.stopSelectionMode()
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {

        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                    is CardSetsEvents.NavigateCreateCardSet -> {
                        findNavController().navigate(R.id.action_main_to_create_card_set)
                    }
                    is CardSetsEvents.ShowConfirmDeleteDialog -> {
                        showConfirmDeleteCardSetDialog()
                    }
                    is CardSetsEvents.ShowCardSetDeleteError -> {
                        showCardSetDeleteErrorDialog()
                    }
                    is CardSetsEvents.NavigateEditCardSet -> {
                        val action = CardSetsFragmentDirections.actionMainToCreateCardSet(evt.id)
                        findNavController().navigate(action)
                    }
                    is CardSetsEvents.NavigateCardSetDetail -> {
                        val action = CardSetsFragmentDirections.actionMainToCardSetDetail(evt.id, evt.title)
                        findNavController().navigate(action)
                    }
                }
            }
        })

        mViewModel.mCardSets.observe(this, Observer { list ->
            run {
                if (this::cardSetAdapter.isInitialized) {
                    cardSetAdapter.listItems = list
                    cardSetAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
        mViewModel.mCardSets.removeObservers(this)
    }

    fun showConfirmDeleteCardSetDialog() {
        DecisionDialog(context!!,
            Constants.DIALOG_CONFIRM_DELETE_CARD_SET,
            R.string.dialog_confirm_delete_card_set_title,
            R.string.dialog_confirm_delete_card_set_text,
            R.string.yes_delete,
            R.string.cancel,
            object : DecisionDialog.DecisionDialogResultReceiver {
                override fun onConfirm(dialogId: Int) {
                    mViewModel.onCardSetDelete()
                }

                override fun onCancel(dialogId: Int) {
                    // Do nothing on cancel
                }
            }
        )
    }

    fun showCardSetDeleteErrorDialog() {
        InfoDialog(context!!,
            Constants.DIALOG_DELETE_CARD_SET_ERROR,
            R.string.dialog_delete_card_set_error_title,
            R.string.dialog_delete_card_set_error_text,
            R.string.ok,
            object : InfoDialog.InfoDialogResultReceiver {
                override fun onConfirm(dialogId: Int) {
                    // Do nothing
                }
            })
    }

    @SuppressLint("CheckResult")
    fun checkSorageIOPermissions() {
        RealRxPermission.getInstance(context)
            .requestEach(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe {
                if (it.name().equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && it.state() == Permission.State.GRANTED) {
                    readExtStoragePermGranted = true
                }
                if (it.name().equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && it.state() == Permission.State.GRANTED) {
                    writeExtStoragePermGranted = true
                }

                if (readExtStoragePermGranted && writeExtStoragePermGranted) {
                    doExport()
                }
            }
    }

    fun doExport() {
        mViewModel.exportCardSets(cardSetAdapter.getSelectedItems())
    }
}
