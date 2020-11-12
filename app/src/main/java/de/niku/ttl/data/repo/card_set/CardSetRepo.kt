package de.niku.ttl.data.repo.card_set

import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.model.LearnStat
import de.niku.ttl.model.Question
import io.reactivex.Observable

interface CardSetRepo {

    fun fetchCardSets(): Observable<List<CardSet>>
    fun fetchCardSet(id: Long): Observable<CardSet>
    fun createCardSet(name: String,
                      cards: List<Card>,
                      questions: List<Question>): Observable<CardSet>
    fun createCardSets(list: List<CardSet>): Observable<Boolean>
    fun deleteCardSet(id: Long): Observable<Boolean>
    fun updateCardSet(cardSet: CardSet): Observable<Boolean>
    fun updateStartedColumn(id: Long, value: Int): Observable<Boolean>
    fun updateCompletedColumn(id: Long, value: Int): Observable<Boolean>

    fun addLearnStat(id: Long, learnStat: LearnStat): Observable<Boolean>
}