package de.niku.ttl.view.fragment_quiz

sealed class QuizEvents {
    class StopSelectionTimer(): QuizEvents()
    class StartSelectionTimer(): QuizEvents()
    class CardSetDone(): QuizEvents()
    class CloseView(): QuizEvents()
}