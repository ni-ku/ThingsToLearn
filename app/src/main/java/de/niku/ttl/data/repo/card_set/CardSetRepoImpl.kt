package de.niku.ttl.data.repo.card_set

import android.annotation.SuppressLint
import de.niku.ttl.data.db.dao.CardDao
import de.niku.ttl.data.db.dao.CardSetDao
import de.niku.ttl.data.db.dao.CardSetWithCardsDao
import de.niku.ttl.data.db.dao.LearnStatDao
import de.niku.ttl.data.db.entity.CardSetWithCards
import de.niku.ttl.data.db.entity.TblCard
import de.niku.ttl.data.db.entity.TblCardSet
import de.niku.ttl.data.db.entity.TblLearnStat
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.model.LearnStat
import io.reactivex.Observable

class CardSetRepoImpl(
    val cardSetDao: CardSetDao,
    val cardDao: CardDao,
    val cardSetWithCardsDao: CardSetWithCardsDao,
    val learnStatDao: LearnStatDao
) : CardSetRepo {

    @SuppressLint("CheckResult")
    override fun fetchCardSets(): Observable<List<CardSet>> {
        return Observable.create<List<CardSetWithCards>> {
            run {

                val cardSets = cardSetWithCardsDao.getCardSets()
                it.onNext(cardSets)

            }
        }
            .flatMap { list ->
                run {
                    if (list.isEmpty()) {
                        var emptyList: List<CardSet> = mutableListOf()
                        Observable.just(emptyList)
                    } else {
                        Observable.fromIterable(list)
                            .map { item ->
                                run {
                                    val cards: MutableList<Card> = mutableListOf()
                                    for (c in item.cards) {
                                        var card = Card(
                                            c.id,
                                            c.front,
                                            c.back
                                        )
                                        cards.add(card)
                                    }

                                    val stats: MutableList<LearnStat> = mutableListOf()
                                    for (s in item.stats) {
                                        var ls = LearnStat(
                                            s.id,
                                            s.date,
                                            s.wrong,
                                            s.right,
                                            s.duration
                                        )
                                        stats.add(ls)
                                    }

                                    var cs = CardSet(
                                        item.cardSet.id,
                                        item.cardSet.name,
                                        item.cardSet.cardCnt,
                                        cards,
                                        stats,
                                        item.cardSet.started,
                                        item.cardSet.completed
                                    )
                                    return@run cs
                                }
                            }
                            .toList()
                            .toObservable()
                    }
                }
            }
    }

    override fun fetchCardSet(id: Long): Observable<CardSet> {
        return Observable.create<CardSetWithCards> {
            run {
                val cardSet = cardSetWithCardsDao.getCardSetById(id)
                it.onNext(cardSet)
            }
        }
            .map { item ->
                run {
                    val cards: MutableList<Card> = mutableListOf()
                    for (c in item.cards) {
                        var card = Card(
                            c.id,
                            c.front,
                            c.back
                        )
                        cards.add(card)
                    }

                    val stats: MutableList<LearnStat> = mutableListOf()
                    for (s in item.stats) {
                        var ls = LearnStat(
                            s.id,
                            s.date,
                            s.wrong,
                            s.right,
                            s.duration
                        )
                        stats.add(ls)
                    }

                    var cs = CardSet(
                        item.cardSet.id,
                        item.cardSet.name,
                        item.cardSet.cardCnt,
                        cards,
                        stats,
                        item.cardSet.started,
                        item.cardSet.completed
                    )
                    return@run cs
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun createCardSet(
        name: String,
        cards: List<Card>
    ): Observable<CardSet> {
        return Observable.create<CardSet> {
            run {
                val cardSetTbl = TblCardSet(null, name, cards.size, 0, 0)
                val cardSetId = cardSetDao.insert(cardSetTbl)

                for (card in cards) {
                    var tblCard = TblCard(null, card.front, card.back, cardSetId)
                    cardDao.insert(tblCard)
                }

                val cardSet = CardSet(cardSetId, name, cards.size, mutableListOf(), mutableListOf(), 0 ,0)
                it.onNext(cardSet)

            }
        }
    }

    override fun createCardSets(list: List<CardSet>): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                for (cs in list) {
                    val cardSetTbl = TblCardSet(null, cs.name, cs.cards.size, cs.started, cs.completed)
                    val cardSetId = cardSetDao.insert(cardSetTbl)

                    if (cs.cards != null) {
                        for (card in cs.cards) {
                            var tblCard = TblCard(null, card.front, card.back, cardSetId)
                            cardDao.insert(tblCard)
                        }
                    }

                    if (cs.stats != null) {
                        for (stat in cs.stats) {
                            var tblLearnStat = TblLearnStat(null, stat.date, stat.wrong, stat.right, stat.duration, cardSetId)
                            learnStatDao.insert(tblLearnStat)
                        }
                    }
                }
                it.onNext(true)
            }
        }
    }

    override fun deleteCardSet(id: Long): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                var deletedRowsCnt = cardSetDao.deleteById(id)
                if (deletedRowsCnt > 0) {
                    it.onNext(true)
                } else {
                    it.onError(Throwable("Could not find entry with given id."))
                }
            }
        }
    }

    override fun updateCardSet(cardSet: CardSet): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                val cardSetTbl = TblCardSet(
                    cardSet.id,
                    cardSet.name,
                    cardSet.cards.size,
                    cardSet.started,
                    cardSet.completed
                )
                cardSetDao.update(cardSetTbl)

                cardDao.deleteAllCardsForCardSet(cardSet.id!!)
                for (card in cardSet.cards) {
                    var tblCard = TblCard(card.id, card.front, card.back, cardSet.id!!)
                    cardDao.insert(tblCard)
                }

                it.onNext(true)
            }
        }
    }

    override fun updateStartedColumn(id: Long, value: Int): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                cardSetDao.updateStartedColumn(id, value)
                it.onNext(true)
            }
        }
    }

    override fun updateCompletedColumn(id: Long, value: Int): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                cardSetDao.updateCompletedColumn(id, value)
                it.onNext(true)
            }
        }
    }

    override fun addLearnStat(id: Long, learnStat: LearnStat): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                val tblls = TblLearnStat(
                    null,
                    learnStat.date,
                    learnStat.wrong,
                    learnStat.right,
                    learnStat.duration,
                    id
                )

                learnStatDao.insert(tblls)
                it.onNext(true)
            }
        }
    }
}

