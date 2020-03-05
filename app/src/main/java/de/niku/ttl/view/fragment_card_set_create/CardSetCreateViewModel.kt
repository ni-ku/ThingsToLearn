package de.niku.ttl.view.fragment_card_set_create

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.view.fragment_card_create.CardEditParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetCreateViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetCreateEvents>() {

    val name: MutableLiveData<String> = MutableLiveData()
    val nameError : MutableLiveData<String> = MutableLiveData()
    val cards: MutableLiveData<MutableList<Card>> = MutableLiveData()

    val editCardSet: MutableLiveData<CardSet> = MutableLiveData()

    init {
        cards.value = mutableListOf()
    }

    @SuppressLint("CheckResult")
    fun fetchCardSetById(id: Long) {
        cardSetRepo.fetchCardSet(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cardSet ->
                run {
                    editCardSet.value = cardSet
                    name.value = cardSet.name
                    cards.value = cardSet.cards.toMutableList()
                }

            }, { error ->
                run {
                }
            })
    }

    fun createCardClick() {
        mEvents.value = CardSetCreateEvents.ShowCreateCardDialog()
    }

    fun addCard(front: String, back: String) {
        var card = Card(
            null,
            front,
            back
        )
        cards.value?.add(card)
        cards.value = cards.value
    }

    fun onCardEdit(position: Int) {
        val params = CardEditParams(cards.value!!.get(position), position)
        mEvents.value = CardSetCreateEvents.ShowEditCardDialog(params)
    }

    fun onCardEdited(position: Int, front: String, back: String) {
        var card = Card(
            cards.value?.get(position)?.id,
            front,
            back
        )

        cards.value?.removeAt(position)
        cards.value?.add(position, card)
        cards.value = cards.value
    }

    fun onCardDelete(position: Int) {
        cards.value?.removeAt(position)
        cards.value = cards.value
    }

    fun onFinishClick() {
        if (editCardSet.value == null) {
            finishCreateCardSet()
        } else {
            finishEditCardSet()
        }
    }

    @SuppressLint("CheckResult")
    fun finishCreateCardSet() {
        val nameValue = name.value
        if (nameValue == null || nameValue.isEmpty()) {
            nameError.value = mResHelper?.getString(R.string.create_card_set_from_error_name_empty)
            return
        } else {
            nameError.value = ""
        }

        val cardSetName = name.value
        val cardSetCards = cards.value

        cardSetRepo.createCardSet(
            cardSetName!!,
            cardSetCards!!.toList()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ cardSet ->
                run {
                    mEvents.value = CardSetCreateEvents.CardSetCreateSuccess()
                }

            }, {error ->
                run {
                }
            })
    }

    @SuppressLint("CheckResult")
    fun finishEditCardSet() {

        var cardSet = CardSet(
            editCardSet.value!!.id,
            name.value!!,
            cards.value!!.size,
            cards.value!!.toList(),
            mutableListOf(),
            editCardSet.value!!.started,
            editCardSet.value!!.completed
        )

        cardSetRepo.updateCardSet(cardSet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ v ->
                run {
                    mEvents.value = CardSetCreateEvents.CardSetCreateSuccess()
                }
            }, { error ->
                run {

                }
            })
    }
}