package de.niku.braincards.view.fragment_card_set_detail

sealed class CardSetDetailEvents {
    class ShowStartLearningDialog(): CardSetDetailEvents()
    class NavigateToViewCards(val id: Long, val title: String): CardSetDetailEvents()
}