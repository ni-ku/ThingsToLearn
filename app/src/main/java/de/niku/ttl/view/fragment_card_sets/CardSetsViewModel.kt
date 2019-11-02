package de.niku.ttl.view.fragment_card_sets

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.common.base.ViewState
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.CardSet
import de.niku.ttl.util.hasExternalStorage
import de.niku.ttl.util.isExternalStorageWriteable
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

    @SuppressLint("CheckResult")
    fun importCardSetsFromJson(json: String) {

        var gson = Gson()
        var cardsets: List<CardSet> = gson.fromJson(json, Array<CardSet>::class.java).toList()

        cardSetRepo.createCardSets(cardsets)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ cardSet ->
                run {
                    mEvents.value = CardSetsEvents.ShowImportSuccess()
                }

            }, {error ->
                run {
                    //mEvents.value = CardSetsEvents.ShowImportSuccess()
                }
            })
    }
}