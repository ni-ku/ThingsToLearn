package de.niku.ttl.view.fragment_quiz

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuizViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<QuizEvents>() {

    companion object {
        const val OPTION_NONE: Int = -1
        const val OPTION_1: Int = 0
        const val OPTION_2: Int = 1
        const val OPTION_3: Int = 2
        const val OPTION_4: Int = 3
    }

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    val vdQuestion: MutableLiveData<String> = MutableLiveData()
    val vdOption1Text: MutableLiveData<String> = MutableLiveData()
    val vdOption1Color: MutableLiveData<Int> = MutableLiveData()
    val vdOption2Text: MutableLiveData<String> = MutableLiveData()
    val vdOption2Color: MutableLiveData<Int> = MutableLiveData()
    val vdOption3Text: MutableLiveData<String> = MutableLiveData()
    val vdOption3Color: MutableLiveData<Int> = MutableLiveData()
    val vdOption4Text: MutableLiveData<String> = MutableLiveData()
    val vdOption4Color: MutableLiveData<Int> = MutableLiveData()
    val cardOptionIndices: MutableLiveData<IntArray> = MutableLiveData()
    var mCurIndex: Int = -1
    var correctOption: Int = -1
    var optionSelected: Boolean = false

    val colorDefault: Int = Color.parseColor("#ffffffff")
    val colorCorrect: Int = Color.parseColor("#ff00ff00")
    val colorWrong: Int = Color.parseColor("#ffff0000")

    init {
        cardOptionIndices.value = intArrayOf(-1, -1, -1, -1)
        resetOptionColors()
    }

    fun initById(id: Long) {
        fetchCardSet(id)
    }

    @SuppressLint("CheckResult")
    fun fetchCardSet(id: Long) {

        cardSetRepo.fetchCardSet(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cs ->
                run {
                    cardSet.value = cs
                    onNext()
                }
            }, { error ->
                run {

                }
            })
    }


    fun onOptionSelected(option: Int) {
        if (optionSelected) {
            onNext()
        } else {
            optionSelected = true
            if (option != correctOption) {
                setOptionColor(option, colorWrong)
            }
            setOptionColor(correctOption, colorCorrect)
            mEvents.value = QuizEvents.StopSelectionTimer()
        }
    }

    fun onNext() {
        optionSelected = false
        mCurIndex++
        if (mCurIndex == cardSet.value?.cardCnt) {
            mCurIndex = 0
        }

        val card = cardSet.value!!.cards.get(mCurIndex)
        vdQuestion.value = card.front

        resetCardOptionIndices()
        resetOptionColors()
        var rndPos = randFromRange(0, 3)
        correctOption = rndPos
        cardOptionIndices.value!![rndPos] = mCurIndex
        setOptionValue(card, rndPos)

        var rndSetIdx = 0
        var rndCard: Card? = null
        for (i in cardOptionIndices.value!!.indices) {
            if (cardOptionIndices.value!![i] < 0) {
                rndSetIdx = selectRandomUnusedCardIndex()
                cardOptionIndices.value!![i] = rndSetIdx
                rndCard = cardSet.value!!.cards.get(rndSetIdx)
                setOptionValue(rndCard, i)
            }
        }

        mEvents.value = QuizEvents.StartSelectionTimer()
    }

    fun setOptionValue(card: Card, option: Int) {
        when (option) {
            0 -> {
                vdOption1Text.value = card.back
            }
            1 -> {
                vdOption2Text.value = card.back
            }
            2 -> {
                vdOption3Text.value = card.back
            }
            3 -> {
                vdOption4Text.value = card.back
            }
        }
    }

    fun setOptionColor(option: Int, color: Int) {
        when (option) {
            0 -> {
                vdOption1Color.value = color
            }
            1 -> {
                vdOption2Color.value = color
            }
            2 -> {
                vdOption3Color.value = color
            }
            3 -> {
                vdOption4Color.value = color
            }
        }
    }

    fun resetOptionColors() {
        setOptionColor(0, colorDefault)
        setOptionColor(1, colorDefault)
        setOptionColor(2, colorDefault)
        setOptionColor(3, colorDefault)
    }

    fun selectRandomUnusedCardIndex(): Int {
        val min = 0
        val max = cardSet.value!!.cardCnt - 1
        var rndSetIdx = randFromRange(min, max)
        while (cardOptionIndices.value!!.contains(rndSetIdx)) {
            rndSetIdx = randFromRange(min, max)
        }
        return rndSetIdx
    }

    fun resetCardOptionIndices() {
        for (i in cardOptionIndices.value!!.indices) {
            cardOptionIndices.value!![i] = -1
        }

    }

    fun randFromRange(min: Int, max: Int): Int {
        return min + (Math.random() * ((max - min) + 1)).toInt()
    }
}