package de.niku.braincards.data.export

import de.niku.braincards.model.CardSet

interface BrainCardsFileIo {
    fun writeCardSetsToFile(list: List<CardSet>, fileName: String) {

    }
}