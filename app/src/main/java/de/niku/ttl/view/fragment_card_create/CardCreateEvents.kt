package de.niku.ttl.view.fragment_card_create

sealed class CardCreateEvents {
    class Done(val result: CardCreateResultData): CardCreateEvents()
    class Cancel(): CardCreateEvents()

    class EditDone(val result: CardEditResultData): CardCreateEvents()
}