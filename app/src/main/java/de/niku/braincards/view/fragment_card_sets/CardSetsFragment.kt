package de.niku.braincards.view.fragment_card_sets

import android.os.Bundle
import android.view.View
import de.niku.braincards.BR

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.databinding.FragmentCardSetsBinding

class CardSetsFragment : BaseFragment<FragmentCardSetsBinding, CardSetsViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_card_sets
    override fun getViewBindingId(): Int = BR.viewmodel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.fetchCardSets()
    }
}
