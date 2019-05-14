package de.niku.braincards.view.fragment_card_set_detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.braincards.common.base.BaseViewModel
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetDetailViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetDetailEvents>() {

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    val vdName: MutableLiveData<String> = MutableLiveData()

    init {

    }

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
                    vdName.value = cs.name
                }
            }, { error ->
                run {

                }
            })
    }

    fun onStartLearningClick() {
        mEvents.value = CardSetDetailEvents.ShowStartLearningDialog()
    }

    fun onViewCardsClick() {
        mEvents.value = CardSetDetailEvents.NavigateToViewCards(
            cardSet.value?.id!!,
            cardSet.value?.name!!
        )
    }
}