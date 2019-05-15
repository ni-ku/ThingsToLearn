package de.niku.braincards.view.fragment_card_set_view_cards

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.braincards.common.base.BaseViewModel
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.model.Card
import de.niku.braincards.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewCardsViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<ViewCardsEvents>() {

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    val cards: MutableLiveData<List<Card>> = MutableLiveData()

    fun initById(id: Long) {
        fetchCardSet(id)
    }

    @SuppressLint("CheckResult")
    fun fetchCardSet(id: Long) {

        cardSetRepo.fetchCardSet(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({cs ->
                run {
                    cardSet.value = cs
                    cards.value = cs.cards
                }
            }, { error ->
                run {

                }
            })
    }

}