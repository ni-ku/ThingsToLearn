package de.niku.ttl.view.fragment_quiz

sealed class QuizEvents {
    object StopSelectionTimer : QuizEvents()
    object StartSelectionTimer : QuizEvents()
    object CardSetDone : QuizEvents()
    object CloseView : QuizEvents()
}