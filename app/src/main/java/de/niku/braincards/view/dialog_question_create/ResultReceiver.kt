package de.niku.braincards.view.dialog_question_create

interface ResultReceiver {
    fun onCreateQuestion(result: QuestionCreateResult)
    fun onEditQuestion(result: QuestionEditResultData)
}