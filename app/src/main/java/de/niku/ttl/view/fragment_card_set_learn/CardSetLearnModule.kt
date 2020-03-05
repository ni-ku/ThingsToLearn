package de.niku.ttl.view.fragment_card_set_learn

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.repo.card_set.CardSetRepo

@Module
class CardSetLearnModule {

    @Provides
    fun provideCardSetLearnViewModel(cardSetRepo: CardSetRepo): CardSetLearnViewModel {
        return CardSetLearnViewModel(cardSetRepo)
    }
}