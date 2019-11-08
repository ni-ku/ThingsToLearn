package de.niku.ttl.view.fragment_card_set_detail

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetDetailViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetDetailEvents>() {

    companion object {
        val TAG: String = CardSetDetailViewModel.toString()
    }

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    val vdName: MutableLiveData<String> = MutableLiveData()
    val vdLearnMode: MutableLiveData<Int> = MutableLiveData()
    val vdViceVersa: MutableLiveData<Boolean> = MutableLiveData()
    val vdViceVersaEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val vdShuffle: MutableLiveData<Boolean> = MutableLiveData()
    val vdCompleted: MutableLiveData<Float> = MutableLiveData()
    val vdCorrect: MutableLiveData<Float> = MutableLiveData()
    val vdLastTimePlayed: MutableLiveData<String> = MutableLiveData()

    init {
        vdLearnMode.value = R.id.rb_mode_normal
        vdViceVersa.value = false
        vdViceVersaEnabled.value = true
        vdShuffle.value = false
        vdCompleted.value = 0f
        vdCorrect.value = 0f

        vdLearnMode.observeForever {
            vdViceVersaEnabled.value = it == R.id.rb_mode_normal
        }
    }

    /**
     * Initializes member from string resources after the
     * lifecycle aware ResourceHelper has been created.
     */
    fun initFromStringRes() {
        vdLastTimePlayed.value = mResHelper.getString(R.string.never)
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
                    calcStats()
                }
            }, { error ->
                run {

                }
            })
    }

    fun onStartLearningClick() {
        var params = StartLearningParams(
            cardSet.value?.id!!,
            cardSet.value?.name!!,
            vdViceVersa.value!!,
            vdShuffle.value!!
        )
        when (vdLearnMode.value) {
            R.id.rb_mode_normal -> {
                mEvents.value = CardSetDetailEvents.NavigateToLearnView(params)
            }
            R.id.rb_mode_quiz -> {
                mEvents.value = CardSetDetailEvents.NavigateToQuizView(params)
            }
        }
    }

    fun onViewCardsClick() {
        mEvents.value = CardSetDetailEvents.NavigateToViewCards(
            cardSet.value?.id!!,
            cardSet.value?.name!!
        )
    }

    fun calcStats() {

        if (cardSet.value!!.started >= 1) {
            val complPercent: Float =
                ((100.0 / cardSet.value!!.started) * cardSet.value!!.completed).toFloat()
            vdCompleted.value = complPercent
        }

        if (cardSet.value!!.stats != null) {
            var wrong = 0
            var right = 0
            var lastTimePlayed = cardSet.value!!.stats[0].date
            for (ls in cardSet.value!!.stats) {
                wrong += ls.wrong
                right += ls.right

                if (ls.date > lastTimePlayed) {
                    lastTimePlayed = ls.date
                }
            }

            val rightPercent: Float = ((100.0 / (wrong + right)) * right).toFloat()
            vdCorrect.value = rightPercent

            vdLastTimePlayed.value = DateFormat.format("dd.MM.yyyy hh:mm", lastTimePlayed).toString()
        }
    }
}