package de.niku.braincards.di.module

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.data.repo.card_set.CardSetRepoImpl
import javax.inject.Singleton

@Module
class DataRepoModule {

    @Provides
    @Singleton
    fun provideCardSetRepo(cardSetDao: CardSetDao) : CardSetRepo {
        return CardSetRepoImpl(cardSetDao)
    }
}