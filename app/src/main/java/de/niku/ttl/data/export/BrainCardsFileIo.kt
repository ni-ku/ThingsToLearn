package de.niku.ttl.data.export

import de.niku.ttl.model.CardSet

interface BrainCardsFileIo {
    fun writeCardSetsToFile(list: List<CardSet>, fileName: String) {

    }
}