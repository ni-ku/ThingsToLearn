package de.niku.ttl.view.fragment_card_sets

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.fs.FsRepo
import de.niku.ttl.data.repo.card_set.CardSetRepo

@Module
class CardSetsModule {

    @Provides
    fun provideCardSetsViewModel(cardSetRepo: CardSetRepo,
                                 fsRepo: FsRepo) : CardSetsViewModel {
        return CardSetsViewModel(cardSetRepo, fsRepo)
    }
}