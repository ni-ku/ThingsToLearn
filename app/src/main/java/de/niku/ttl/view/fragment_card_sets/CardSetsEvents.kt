package de.niku.ttl.view.fragment_card_sets

sealed class CardSetsEvents() {
    object NavigateCreateCardSet : CardSetsEvents()
    class NavigateEditCardSet(val id: Long): CardSetsEvents()
    object ShowConfirmDeleteDialog : CardSetsEvents()
    object ShowCardSetDeleteError : CardSetsEvents()
    class NavigateCardSetDetail(val id: Long, val title: String): CardSetsEvents()
    object ShowImportSuccess : CardSetsEvents()
    class ShowExportSuccess(val message: String): CardSetsEvents()
    object ShowExportError : CardSetsEvents()
}