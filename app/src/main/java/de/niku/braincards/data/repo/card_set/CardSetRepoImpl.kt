package de.niku.braincards.data.repo.card_set

import android.annotation.SuppressLint
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.db.entity.TblCardSet
import de.niku.braincards.model.CardSet
import io.reactivex.Observable

class CardSetRepoImpl(
    val cardSetDao: CardSetDao
) : CardSetRepo {

    @SuppressLint("CheckResult")
    override fun fetchCardSets(): Observable<List<CardSet>> {
        return Observable.create<List<TblCardSet>> {
            run {
                val cardSets = cardSetDao.getCardSets()
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
                                var cs = CardSet(
                                    item.id,
                                    item.name,
                                    item.cardCnt
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

}

