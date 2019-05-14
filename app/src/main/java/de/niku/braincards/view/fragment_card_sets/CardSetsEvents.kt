package de.niku.braincards.view.fragment_card_sets

sealed class CardSetsEvents() {
    class NavigateCreateCardSet(): CardSetsEvents()
    class ShowConfirmDeleteDialog(): CardSetsEvents()
    class ShowCardSetDeleteError(): CardSetsEvents()
}