package de.niku.braincards.view.fragment_card_set_view_cards


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.niku.braincards.BR

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.databinding.FragmentViewCardsBinding

class ViewCardsFragment : BaseFragment<FragmentViewCardsBinding, ViewCardsViewModel>() {

    val args: ViewCardsFragmentArgs by navArgs()

    lateinit var cardsAdapter: CardAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_view_cards
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()

        if (!this::cardsAdapter.isInitialized) {
            cardsAdapter = CardAdapter(mViewModel)
        }

        mDataBinding.rvCards.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvCards.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mDataBinding.rvCards.setHasFixedSize(true)
        mDataBinding.rvCards.adapter = cardsAdapter

        val cardSetId = args.cardSetId
        mViewModel.initById(cardSetId)
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                }
            }
        })

        mViewModel.cards.observe(this, Observer { list ->
            run {
                if (this::cardsAdapter.isInitialized) {
                    cardsAdapter.listItems = list.toMutableList()
                    cardsAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
        mViewModel.cards.removeObservers(this)
    }

}
