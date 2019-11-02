package de.niku.ttl.view.fragment_card_set_detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.Constants
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.CardSet
import de.niku.ttl.view.dialog_start_learning.StartLearningResultData
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

    fun onStartLearningResult(resultData: StartLearningResultData) {
        if (resultData.learnMode == Constants.LEARN_MODE_NORMAL) {
            var params = StartLearningParams(
                cardSet.value?.id!!,
                cardSet.value?.name!!,
                resultData.learnMode
            )
            mEvents.value = CardSetDetailEvents.NavigateToLearnView(params)
        } else if (resultData.learnMode == Constants.LEARN_MODE_QUIZ) {
            mEvents.value = CardSetDetailEvents.NavigateToQuizView(cardSet.value?.id!!, cardSet.value?.name!!)
        }
    }

    fun onViewCardsClick() {
        mEvents.value = CardSetDetailEvents.NavigateToViewCards(
            cardSet.value?.id!!,
            cardSet.value?.name!!
        )
    }
}