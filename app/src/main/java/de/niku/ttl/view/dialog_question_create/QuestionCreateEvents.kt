package de.niku.ttl.view.dialog_question_create

sealed class QuestionCreateEvents {
    class Done(val result: QuestionCreateResult): QuestionCreateEvents()
    class EditDone(val result: QuestionEditParams): QuestionCreateEvents()
    object Cancel: QuestionCreateEvents()
    object SetCursorToPosition: QuestionCreateEvents()
}
