package de.niku.braincards.di.module

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.data.repo.card_set.CardSetRepoImpl
import javax.inject.Singleton

@Module
class DataRepoModule {

    @Provides
    @Singleton
    fun provideCardSetRepo() : CardSetRepo {
        return CardSetRepoImpl()
    }
}