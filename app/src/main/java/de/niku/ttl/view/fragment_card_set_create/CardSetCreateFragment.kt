package de.niku.ttl.view.fragment_card_set_create

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import de.niku.ttl.R
import de.niku.ttl.BR
import de.niku.ttl.common.base.BaseFragment
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.databinding.FragmentCardSetCreateBinding
import de.niku.ttl.view.fragment_card_create.*

class CardSetCreateFragment : BaseFragment<FragmentCardSetCreateBinding, CardSetCreateViewModel>(),
    ResultReceiver {

    val args: CardSetCreateFragmentArgs by navArgs()

    lateinit var cardsAdapter: CardAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_card_set_create
    override fun getViewBindingId(): Int = BR.viewmodel

    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()
        createLifecycleObservers()

        if (!this::cardsAdapter.isInitialized) {
            cardsAdapter = CardAdapter(mViewModel)
        }

        mDataBinding.rvCards.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvCards.adapter = cardsAdapter

        val idToEdit = args.cardSetId
        if (idToEdit != 0L) {
            mViewModel.fetchCardSetById(idToEdit)
        }
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    fun connectObservables() {

        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is CardSetCreateEvents.ShowCreateCardDialog -> {
                        var dialog = CardCreateDialog(this)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.CardSetCreateSuccess -> {
                        findNavController().navigateUp()
                    }
                    is CardSetCreateEvents.ShowEditCardDialog -> {
                        var dialog = CardCreateDialog(this, evt.params)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                }
            }
        })

        mViewModel.cards.observe(this, Observer { list ->
            run {
                if (this::cardsAdapter.isInitialized) {
                    cardsAdapter.listItems = list
                    cardsAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }

    override fun onCreateCard(result: CardCreateResultData) {
        mViewModel.addCard(result.frontValue, result.backValue)
    }

    override fun onEditCard(result: CardEditResultData) {
        mViewModel.onCardEdited(result.position, result.frontValue, result.backValue)
    }
}
