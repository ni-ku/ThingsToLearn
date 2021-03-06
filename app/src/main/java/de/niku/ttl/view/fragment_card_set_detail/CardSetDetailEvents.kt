package de.niku.ttl.view.fragment_card_set_detail

sealed class CardSetDetailEvents {
    class NavigateToViewCards(val id: Long, val title: String): CardSetDetailEvents()
    class NavigateToLearnView(val params: StartLearningParams): CardSetDetailEvents()
    class NavigateToQuizView(val params: StartLearningParams): CardSetDetailEvents()
}