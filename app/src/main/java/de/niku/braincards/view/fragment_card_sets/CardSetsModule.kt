package de.niku.braincards.view.fragment_card_sets

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.repo.card_set.CardSetRepo

@Module
class CardSetsModule {

    @Provides
    fun provideCardSetsViewModel(cardSetRepo: CardSetRepo) : CardSetsViewModel {
        return CardSetsViewModel(cardSetRepo)
    }
}