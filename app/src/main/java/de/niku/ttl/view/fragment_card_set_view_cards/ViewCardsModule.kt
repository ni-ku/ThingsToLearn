package de.niku.ttl.view.fragment_card_set_view_cards

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.repo.card_set.CardSetRepo

@Module
class ViewCardsModule {

    @Provides
    fun provideViewCardsViewModel(cardSetRepo: CardSetRepo): ViewCardsViewModel {
        return ViewCardsViewModel(cardSetRepo)
    }
}