package de.niku.ttl.data.fs

import de.niku.ttl.model.CardSet
import io.reactivex.Observable

interface FsRepo {
    fun writeCardSetsToFile(list: List<CardSet>) : Observable<String>
}