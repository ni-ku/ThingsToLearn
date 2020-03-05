package de.niku.ttl.view.fragment_quiz

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.repo.card_set.CardSetRepo

@Module
class QuizModule {

    @Provides
    fun provideQuizViewModel(cardSetRepo: CardSetRepo): QuizViewModel {
        return QuizViewModel(cardSetRepo)
    }
}