package de.niku.braincards.view.fragment_card_set_detail

import de.niku.braincards.view.dialog_start_learning.StartLearningResultData

sealed class CardSetDetailEvents {
    class ShowStartLearningDialog(): CardSetDetailEvents()
    class NavigateToViewCards(val id: Long, val title: String): CardSetDetailEvents()
    class NavigateToLearnView(val params: StartLearningResultData): CardSetDetailEvents()
}