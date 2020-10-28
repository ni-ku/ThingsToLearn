package de.niku.ttl.view.fragment_card_set_learn

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.Constants
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
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

    fun onStart() {
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

        val card = cards!!.get(mCurIndex)

        if (!mViceVersa) {
            vdFront.value = card.front
            vdBack.value = card.back
        } else {
            vdFront.value = card.back
            vdBack.value = card.front
        }
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