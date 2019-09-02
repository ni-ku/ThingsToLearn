package de.niku.braincards.view.fragment_card_sets

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import de.niku.braincards.common.base.BaseViewModel
import de.niku.braincards.common.base.ViewState
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.model.CardSet
import de.niku.braincards.util.hasExternalStorage
import de.niku.braincards.util.isExternalStorageWriteable
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
        mEvents.value = CardSetsEvents.NavigateEditCardSet(mCardSets.value!!.get(position).id!!)
    }

    fun onCardSetDeleteClick(position: Int) {
        mTmpPos = position
        mEvents.value = CardSetsEvents.ShowConfirmDeleteDialog()
    }

    @SuppressLint("CheckResult")
    fun onCardSetDelete() {
        if (mTmpPos != -1) {
            cardSetRepo.deleteCardSet(mCardSets.value!!.get(mTmpPos).id!!)
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

    fun onShowCardSetDetailClick(position: Int) {
        mEvents.value = CardSetsEvents.NavigateCardSetDetail(
            mCardSets.value?.get(position)?.id!!,
            mCardSets.value?.get(position)?.name!!
        )
    }

    fun exportCardSets(cardSets: List<CardSet>) {
        if (cardSets.isEmpty()) {
            return
        }

        if (hasExternalStorage() && isExternalStorageWriteable()) {

        }
    }
}