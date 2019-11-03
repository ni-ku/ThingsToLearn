package de.niku.ttl.di.module

import dagger.Module
import dagger.Provides
import de.niku.ttl.data.db.dao.CardDao
import de.niku.ttl.data.db.dao.CardSetDao
import de.niku.ttl.data.db.dao.CardSetWithCardsDao
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.data.repo.card_set.CardSetRepoImpl
import javax.inject.Singleton

@Module
class DataRepoModule {

    @Provides
    @Singleton
    fun provideCardSetRepo(cardSetDao: CardSetDao,
                           cardDao: CardDao,
                           cardSetWithCardsDao: CardSetWithCardsDao) : CardSetRepo {
        return CardSetRepoImpl(
            cardSetDao,
            cardDao,
            cardSetWithCardsDao
        )
    }
}