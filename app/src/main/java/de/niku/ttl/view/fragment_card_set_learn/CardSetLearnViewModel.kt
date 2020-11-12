package de.niku.ttl.view.fragment_card_set_learn

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.model.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetLearnViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetLearnEvents>() {

    val cardSet: MutableLiveData<CardSet> = MutableLiveData()
    lateinit var cards: MutableList<Card>
    val vdFront: MutableLiveData<String> = MutableLiveData()
    val vdBack: MutableLiveData<String> = MutableLiveData()
    val vdShowBack: MutableLiveData<Boolean> = MutableLiveData()
    var mCurIndex: Int = -1
    var mViceVersa: Boolean = false
    var mShuffle: Boolean = false

    fun initById(id: Long, viceVersa: Boolean, shuffle: Boolean) {
        mViceVersa = viceVersa
        mShuffle = shuffle
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
                    cards = cs.cards.toMutableList()
                    onStart()
                }
            }, { error ->
                run {

                }
            })
    }

    private fun onStart() {
        if (mShuffle) {
            cards.shuffle()
        }

        cardSet.value!!.started += 1
        updateStartedCol()

        onNextClick()
    }

    @SuppressLint("CheckResult")
    fun onRestart() {
        onStart()
    }

    fun onRevealCardClick() {
        vdShowBack.value = true
    }

    fun onNextClick() {
        vdShowBack.value = false
        mCurIndex++
        if (mCurIndex == cards!!.size) {
            cardSet.value!!.completed += 1
            updateCompletedCol()
            mCurIndex = -1
            mEvents.value = CardSetLearnEvents.CardSetDone
            return
        }

        val card = cards!![mCurIndex]

        var front = card.front
        var back = card.back

        if (cardSet.value!!.questions.isNotEmpty()) {
            val question = cardSet.value!!.questions[0]
            front = parseQuestionAndReplacePlaceholder(question, card)
        }

        setFrontBackValue(front, back)
    }

    private fun setFrontBackValue(front: String, back: String) {
        if (!mViceVersa) {
            vdFront.value = front
            vdBack.value = back
        } else {
            vdFront.value = back
            vdBack.value = front
        }
    }

    private fun parseQuestionAndReplacePlaceholder(question: Question, card: Card): String {
        val idxStart: Pair<Int, String>? = question.text.findAnyOf(listOf("{"), 0, false)
        val idxEnd: Pair<Int, String>? = question.text.findAnyOf(listOf("}"), 0, false)
        val q1: String = question.text.subSequence(0, idxStart!!.first).toString()
        val q2: String = question.text.subSequence(idxEnd!!.first + 1, question.text.length).toString()

        return q1 + card.front + q2
    }

    @SuppressLint("CheckResult")
    fun updateStartedCol() {
        cardSetRepo.updateStartedColumn(cardSet.value!!.id!!, cardSet.value!!.started)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                run {

                }

            }, {
                run {

                }

            })
    }

    @SuppressLint("CheckResult")
    fun updateCompletedCol() {
        cardSetRepo.updateCompletedColumn(cardSet.value!!.id!!, cardSet.value!!.completed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                run {

                }

            }, {
                run {

                }

            })
    }
}