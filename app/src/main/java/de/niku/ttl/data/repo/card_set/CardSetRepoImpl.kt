package de.niku.ttl.data.repo.card_set

import android.annotation.SuppressLint
import de.niku.ttl.data.db.dao.*
import de.niku.ttl.data.db.entity.*
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.model.LearnStat
import de.niku.ttl.model.Question
import io.reactivex.Observable

class CardSetRepoImpl(
    private val cardSetDao: CardSetDao,
    private val cardDao: CardDao,
    private val questionDao: QuestionDao,
    private val cardSetWithCardsDao: CardSetWithCardsDao,
    private val learnStatDao: LearnStatDao
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
                        val emptyList: List<CardSet> = mutableListOf()
                        Observable.just(emptyList)
                    } else {
                        Observable.fromIterable(list)
                            .map { item ->
                                run {
                                    val cards: MutableList<Card> = mutableListOf()
                                    for (c in item.cards) {
                                        val card = Card(
                                            c.id,
                                            c.front,
                                            c.back
                                        )
                                        cards.add(card)
                                    }

                                    val questions: MutableList<Question> = mutableListOf()
                                    for (q in item.questions) {
                                        val question = Question(
                                            q.id,
                                            q.question
                                        )
                                        questions.add(question)
                                    }

                                    val stats: MutableList<LearnStat> = mutableListOf()
                                    for (s in item.stats) {
                                        val ls = LearnStat(
                                            s.id,
                                            s.date,
                                            s.wrong,
                                            s.right,
                                            s.duration
                                        )
                                        stats.add(ls)
                                    }

                                    CardSet(
                                        item.cardSet.id,
                                        item.cardSet.name,
                                        item.cardSet.cardCnt,
                                        cards,
                                        questions,
                                        stats,
                                        item.cardSet.started,
                                        item.cardSet.completed
                                    )
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
                        val card = Card(
                            c.id,
                            c.front,
                            c.back
                        )
                        cards.add(card)
                    }

                    val questions: MutableList<Question> = mutableListOf()
                    for (q in item.questions) {
                        val question = Question(
                            q.id,
                            q.question
                        )
                        questions.add(question)
                    }

                    val stats: MutableList<LearnStat> = mutableListOf()
                    for (s in item.stats) {
                        val ls = LearnStat(
                            s.id,
                            s.date,
                            s.wrong,
                            s.right,
                            s.duration
                        )
                        stats.add(ls)
                    }

                    return@run CardSet(
                        item.cardSet.id,
                        item.cardSet.name,
                        item.cardSet.cardCnt,
                        cards,
                        questions,
                        stats,
                        item.cardSet.started,
                        item.cardSet.completed
                    )
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun createCardSet(
        name: String,
        cards: List<Card>,
        questions: List<Question>
    ): Observable<CardSet> {
        return Observable.create<CardSet> {
            run {
                val cardSetTbl = TblCardSet(null, name, cards.size, 0, 0)
                val cardSetId = cardSetDao.insert(cardSetTbl)

                for (card in cards) {
                    val tblCard = TblCard(null, card.front, card.back, cardSetId)
                    cardDao.insert(tblCard)
                }

                for (question in questions) {
                    val tblQuestion = TblQuestion(null, question.text, cardSetId)
                    questionDao.insert(tblQuestion)
                }

                val cardSet = CardSet(cardSetId, name, cards.size, mutableListOf(), mutableListOf(), mutableListOf(), 0 ,0)
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

                    for (card in cs.cards) {
                        val tblCard = TblCard(null, card.front, card.back, cardSetId)
                        cardDao.insert(tblCard)
                    }

                    for (question in cs.questions) {
                        val tblQuestion = TblQuestion(null, question.text, cardSetId)
                        questionDao.insert(tblQuestion)
                    }

                    for (stat in cs.stats) {
                        val tblLearnStat = TblLearnStat(null, stat.date, stat.wrong, stat.right, stat.duration, cardSetId)
                        learnStatDao.insert(tblLearnStat)
                    }
                }
                it.onNext(true)
            }
        }
    }

    override fun deleteCardSet(id: Long): Observable<Boolean> {
        return Observable.create<Boolean> {
            run {
                val deletedRowsCnt = cardSetDao.deleteById(id)
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
                    val tblCard = TblCard(card.id, card.front, card.back, cardSet.id)
                    cardDao.insert(tblCard)
                }

                questionDao.deleteAllQuestionsFromCardSet(cardSet.id!!)
                for (question in cardSet.questions) {
                    val tblQuestion = TblQuestion(question.id, question.text, cardSet.id!!)
                    questionDao.insert(tblQuestion)
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

