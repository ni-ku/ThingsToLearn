package de.niku.braincards.view.fragment_card_sets

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import de.niku.braincards.common.base.BaseViewModel
import de.niku.braincards.common.base.ViewState
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.NullPointerException

class CardSetsViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetsEvents>() {

    var vdShowLoadingAnimation: MutableLiveData<Boolean> = MutableLiveData()
    var vdViewState: ViewState = ViewState()

    var mCardSets: MutableLiveData<MutableList<CardSet>> = MutableLiveData()
    var mTmpPos = -1


    @SuppressLint("CheckResult")
    fun fetchCardSets() {
        vdShowLoadingAnimation.value = true
        cardSetRepo.fetchCardSets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                run {
                    vdShowLoadingAnimation.value = false
                    if (list == null || list.isEmpty()) {
                        vdViewState.state.value = ViewState.EMPTY
                    } else {
                        vdViewState.state.value = ViewState.CONTENT
                        mCardSets.value = list.toMutableList()
                    }
                }

            }, { error ->
                run {
                    vdShowLoadingAnimation.value = false
                    if (error is NullPointerException) {
                        vdViewState.state.value = ViewState.EMPTY
                    }
                }
            })
    }

    fun createCardSetClick() {
        mEvents.value = CardSetsEvents.NavigateCreateCardSet()
    }

    fun onCardSetEditClick(position: Int) {

    }

    fun onCardSetDeleteClick(position: Int) {
        mTmpPos = position
        mEvents.value = CardSetsEvents.ShowConfirmDeleteDialog()
    }

    @SuppressLint("CheckResult")
    fun onCardSetDelete() {
        if (mTmpPos != -1) {
            cardSetRepo.deleteCardSet(120)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ v ->
                    run {
                        fetchCardSets()
                    }
                }, { error ->
                    run {
                        mEvents.value = CardSetsEvents.ShowCardSetDeleteError()
                    }
                }
                )
        }
    }
}