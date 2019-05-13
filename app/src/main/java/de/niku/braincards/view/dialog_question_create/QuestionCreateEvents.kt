package de.niku.braincards.view.dialog_question_create

sealed class QuestionCreateEvents {
    class Done(val result: QuestionCreateResult): QuestionCreateEvents()
    class EditDone(val result: QuestionEditResultData): QuestionCreateEvents()
    class Cancel(): QuestionCreateEvents()
}