package de.niku.braincards.view.fragment_card_sets

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.niku.braincards.BR
import de.niku.braincards.Constants

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.common.dialogs.DecisionDialog
import de.niku.braincards.common.dialogs.InfoDialog
import de.niku.braincards.databinding.FragmentCardSetsBinding

class CardSetsFragment : BaseFragment<FragmentCardSetsBinding, CardSetsViewModel>() {

    lateinit var cardSetAdapter: CardSetAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_card_sets
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
        mDataBinding.setVariable(BR.viewstate, mViewModel.vdViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()

        if (!this::cardSetAdapter.isInitialized) {
            cardSetAdapter = CardSetAdapter(mViewModel)
        }

        mDataBinding.rvCardSets.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvCardSets.adapter = cardSetAdapter

        mViewModel.fetchCardSets()

    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {

        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is CardSetsEvents.NavigateCreateCardSet -> {
                        findNavController().navigate(R.id.action_main_to_create_card_set)
                    }
                    is CardSetsEvents.ShowConfirmDeleteDialog -> {
                        showConfirmDeleteCardSetDialog()
                    }
                    is CardSetsEvents.ShowCardSetDeleteError -> {
                        showCardSetDeleteErrorDialog()
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
        } )

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
            object: DecisionDialog.DecisionDialogResultReceiver {
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
            object: InfoDialog.InfoDialogResultReceiver {
                override fun onConfirm(dialogId: Int) {
                    // Do nothing
                }
            })
    }
}
