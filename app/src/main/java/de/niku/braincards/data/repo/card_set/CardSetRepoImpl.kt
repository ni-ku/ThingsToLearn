package de.niku.braincards.data.repo.card_set

import android.annotation.SuppressLint
import de.niku.braincards.data.db.dao.CardDao
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.db.dao.CardSetWithCardsDao
import de.niku.braincards.data.db.dao.QuestionsDao
import de.niku.braincards.data.db.entity.CardSetWithCards
import de.niku.braincards.data.db.entity.TblCard
import de.niku.braincards.data.db.entity.TblCardSet
import de.niku.braincards.data.db.entity.TblQuestion
import de.niku.braincards.model.Card
import de.niku.braincards.model.CardSet
import de.niku.braincards.model.Question
import io.reactivex.Observable

class CardSetRepoImpl(
    val cardSetDao: CardSetDao,
    val cardDao: CardDao,
    val questionDao: QuestionsDao,
    val cardSetWithCardsDao: CardSetWithCardsDao
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

                                    val questions: MutableList<Question> = mutableListOf()
                                    for (q in item.questions) {
                                        var question = Question(
                                            q.id,
                                            q.question
                                        )
                                        questions.add(question)
                                    }

                                    var cs = CardSet(
                                        item.cardSet.id,
                                        item.cardSet.name,
                                        item.cardSet.cardCnt,
                                        cards,
                                        questions
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

                    val questions: MutableList<Question> = mutableListOf()
                    for (q in item.questions) {
                        var question = Question(
                            q.id,
                            q.question
                        )
                        questions.add(question)
                    }

                    var cs = CardSet(
                        item.cardSet.id,
                        item.cardSet.name,
                        item.cardSet.cardCnt,
                        cards,
                        questions
                    )
                    return@run cs
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
                val cardSetTbl = TblCardSet(null, name, cards.size)
                val cardSetId = cardSetDao.insert(cardSetTbl)

                for (card in cards) {
                    var tblCard = TblCard(null, card.front, card.back, cardSetId)
                    cardDao.insert(tblCard)
                }

                for (question in questions) {
                    var tblQuestion = TblQuestion(null, question.text, cardSetId)
                    questionDao.insert(tblQuestion)
                }

                val cardSet = CardSet(cardSetId, name, cards.size, mutableListOf(), mutableListOf())
                it.onNext(cardSet)

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
                val cardSetTbl = TblCardSet(cardSet.id, cardSet.name, cardSet.cards.size)
                cardSetDao.update(cardSetTbl)

                cardDao.deleteAllCardsForCardSet(cardSet.id!!)
                for (card in cardSet.cards) {
                    var tblCard = TblCard(card.id, card.front, card.back, cardSet.id!!)
                    cardDao.insert(tblCard)
                }

                questionDao.deleteAllQuestionsForCardSet(cardSet.id!!)
                for (question in cardSet.questions) {
                    var tblQuestion = TblQuestion(question.id, question.text, cardSet.id!!)
                    questionDao.insert(tblQuestion)
                }

                it.onNext(true)
            }
        }
    }
}

