package de.niku.ttl.view.fragment_card_set_detail

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.repo.card_set.CardSetRepo

@Module
class CardSetDetailModule {

    @Provides
    fun provideCardSetDetailViewModel(cardSetRepo: CardSetRepo): CardSetDetailViewModel {
        return CardSetDetailViewModel(cardSetRepo)
    }
}