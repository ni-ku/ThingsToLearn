package de.niku.ttl.view.fragment_card_set_learn

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.Constants
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.CardSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetLearnViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetLearnEvents>() {

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    var learnMode: Int = Constants.LEARN_MODE_NORMAL

    val vdFront: MutableLiveData<String> = MutableLiveData()
    val vdBack: MutableLiveData<String> = MutableLiveData()
    val vdShowBack: MutableLiveData<Boolean> = MutableLiveData()
    var mCurIndex: Int = -1

    fun initById(id: Long, mode: Int) {
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
                    onNextClick()
                }
            }, { error ->
                run {

                }
            })
    }

    fun onRevealCardClick() {
        vdShowBack.value = true
    }

    fun onNextClick() {
        vdShowBack.value = false
        mCurIndex++
        if (mCurIndex == cardSet.value?.cardCnt) {
            mCurIndex = 0
        }

        val card = cardSet.value!!.cards.get(mCurIndex)

        if (cardSet.value!!.questions.isEmpty()) {
            vdFront.value = card.front
        } else {
            val question = cardSet.value!!.questions.get(0)
            val idxStart: Pair<Int, String>? = question.text.findAnyOf(listOf("{"), 0, false)
            val idxEnd: Pair<Int, String>? = question.text.findAnyOf(listOf("}"), 0, false)
            val q1: String = question.text.subSequence(0, idxStart!!.first).toString()
            val q2: String = question.text.subSequence(idxEnd!!.first + 1, question.text.length).toString()

            var fullText = q1 + " " + card.front + " " + q2

            vdFront.value = fullText
        }

        vdBack.value = card.back

    }
}