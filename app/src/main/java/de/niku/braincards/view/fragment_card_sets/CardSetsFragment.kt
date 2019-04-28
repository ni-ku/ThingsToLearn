package de.niku.braincards.view.fragment_card_sets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.niku.braincards.BR

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
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
                    is CardSetsEvents.navigateCreateCardSet -> {
                        Toast.makeText(context, "TODO: Navigate to create cardSet", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        mViewModel.mCardSets.observe(this, Observer { list ->
            run {
                cardSetAdapter = CardSetAdapter(list)
                mDataBinding.rvCardSets.layoutManager = LinearLayoutManager(context)
                mDataBinding.rvCardSets.setHasFixedSize(true)
                mDataBinding.rvCardSets.adapter = cardSetAdapter
            }
        } )

    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
        mViewModel.mCardSets.removeObservers(this)
    }
}
