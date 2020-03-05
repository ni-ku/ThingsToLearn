package de.niku.ttl.view.fragment_card_create

import dagger.Module
import dagger.Provides

@Module
class CardCreateModule {

    @Provides
    fun provideCardCreateViewModel(): CardCreateViewModel {
        return CardCreateViewModel()
    }
}