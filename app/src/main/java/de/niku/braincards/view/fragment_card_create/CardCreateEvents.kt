package de.niku.braincards.view.fragment_card_create

import android.os.Bundle

sealed class CardCreateEvents {
    class Done(val result: CardCreateResultData): CardCreateEvents()
    class Cancel(): CardCreateEvents()

    class EditDone(val result: CardEditResultData): CardCreateEvents()
}