package de.niku.braincards.data.repo.card_set

import de.niku.braincards.model.Card
import de.niku.braincards.model.CardSet
import de.niku.braincards.model.Question
import io.reactivex.Observable

interface CardSetRepo {

    fun fetchCardSets(): Observable<List<CardSet>>
    fun fetchCardSet(id: Long): Observable<CardSet>
    fun createCardSet(name: String,
                      cards: List<Card>,
                      questions: List<Question>): Observable<CardSet>
    fun deleteCardSet(id: Long): Observable<Boolean>
    fun updateCardSet(cardSet: CardSet): Observable<Boolean>
}