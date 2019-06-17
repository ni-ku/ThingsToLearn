package de.niku.braincards.view.fragment_quiz

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.repo.card_set.CardSetRepo

@Module
class QuizModule {

    @Provides
    fun provideQuizViewModel(cardSetRepo: CardSetRepo): QuizViewModel {
        return QuizViewModel(cardSetRepo)
    }
}