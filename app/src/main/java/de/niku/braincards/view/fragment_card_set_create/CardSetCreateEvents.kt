package de.niku.braincards.view.fragment_card_set_create

import de.niku.braincards.view.dialog_question_create.QuestionEditParams
import de.niku.braincards.view.fragment_card_create.CardEditParams

sealed class CardSetCreateEvents {
    class ShowCreateCardDialog(): CardSetCreateEvents()
    class ShowEditCardDialog(val params: CardEditParams): CardSetCreateEvents()
    class ShowCreateQuestionDialog(): CardSetCreateEvents()
    class ShowEditQuestionDialog(val params: QuestionEditParams): CardSetCreateEvents()
    class CardSetCreateSuccess(): CardSetCreateEvents()
}