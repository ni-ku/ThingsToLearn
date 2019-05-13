package de.niku.braincards.view.fragment_card_set_create

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.repo.card_set.CardSetRepo

@Module
class CardSetCreateModule {

    @Provides
    fun provideCardSetCreateViewModel(cardSetRepo: CardSetRepo): CardSetCreateViewModel {
        return CardSetCreateViewModel(cardSetRepo)
    }

}