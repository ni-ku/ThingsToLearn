package de.niku.ttl.view.fragment_card_set_create

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.model.Card
import de.niku.ttl.model.CardSet
import de.niku.ttl.model.Question
import de.niku.ttl.view.dialog_question_create.QuestionEditParams
import de.niku.ttl.view.fragment_card_create.CardEditParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardSetCreateViewModel(
    val cardSetRepo: CardSetRepo
) : BaseViewModel<CardSetCreateEvents>() {

    val name: MutableLiveData<String> = MutableLiveData()
    val nameError : MutableLiveData<String> = MutableLiveData()
    val cards: MutableLiveData<MutableList<Card>> = MutableLiveData()
    val questions: MutableLiveData<MutableList<Question>> = MutableLiveData()

    val editCardSet: MutableLiveData<CardSet> = MutableLiveData()

    init {
        cards.value = mutableListOf()
        questions.value = mutableListOf()
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
                    questions.value = cardSet.questions.toMutableList()
                }

            }, { error ->
                run {
                }
            })
    }

    fun createCardClick() {
        mEvents.value = CardSetCreateEvents.ShowCreateCardDialog()
    }

    fun createQuestionClick() {
        mEvents.value = CardSetCreateEvents.ShowCreateQuestionDialog()
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

    fun addQuestion(text: String) {
        var question = Question(
            null,
            text
        )
        questions.value?.add(question)
        questions.value = questions.value
    }

    fun onQuestionEdit(position: Int) {
        val params = QuestionEditParams(position, questions.value!!.get(position))
        mEvents.value = CardSetCreateEvents.ShowEditQuestionDialog(params)
    }

    fun onQuestionEdited(position: Int, text: String) {
        var question = Question(
            null,
            text
        )
        questions.value?.removeAt(position)
        questions.value?.add(position, question)
        questions.value = questions.value
    }

    fun onQuestionDelete(position: Int) {
        questions.value?.removeAt(position)
        questions.value = questions.value
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
        val cardSetQuestions = questions.value

        cardSetRepo.createCardSet(
            cardSetName!!,
            cardSetCards!!.toList(),
            cardSetQuestions!!.toList()
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
            questions.value!!
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