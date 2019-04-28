package de.niku.braincards.data.repo.card_set

import de.niku.braincards.model.CardSet
import io.reactivex.Observable

interface CardSetRepo {

    fun fetchCardSets(): Observable<List<CardSet>>
}