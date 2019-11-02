package de.niku.ttl.view.fragment_card_create

interface ResultReceiver {
    fun onCreateCard(result: CardCreateResultData)
    fun onEditCard(result: CardEditResultData)
}