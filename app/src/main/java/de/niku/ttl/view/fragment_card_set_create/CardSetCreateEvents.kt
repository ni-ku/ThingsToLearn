package de.niku.ttl.view.fragment_card_set_create

import de.niku.ttl.view.dialog_question_create.QuestionEditParams
import de.niku.ttl.view.fragment_card_create.CardEditParams

sealed class CardSetCreateEvents {
    object ShowCreateCardDialog : CardSetCreateEvents()
    class ShowEditCardDialog(val params: CardEditParams): CardSetCreateEvents()
    object ShowCreateQuestionDialog: CardSetCreateEvents()
    class ShowEditQuestionDialog(val params: QuestionEditParams): CardSetCreateEvents()
    object CardSetCreateSuccess : CardSetCreateEvents()
}