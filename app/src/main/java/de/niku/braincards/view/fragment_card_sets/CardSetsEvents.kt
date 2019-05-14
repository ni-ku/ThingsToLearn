package de.niku.braincards.view.fragment_card_sets

sealed class CardSetsEvents() {
    class NavigateCreateCardSet(): CardSetsEvents()
    class NavigateEditCardSet(val id: Long): CardSetsEvents()
    class ShowConfirmDeleteDialog(): CardSetsEvents()
    class ShowCardSetDeleteError(): CardSetsEvents()
    class NavigateCardSetDetail(val id: Long, val title: String): CardSetsEvents()
}