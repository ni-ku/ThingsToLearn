package de.niku.braincards.view.fragment_card_sets

import de.niku.braincards.common.base.BaseViewModel
import de.niku.braincards.data.repo.card_set.CardSetRepo

class CardSetsViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel() {

    fun fetchCardSets() {
    }
}