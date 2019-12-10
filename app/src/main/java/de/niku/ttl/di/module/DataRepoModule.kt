package de.niku.ttl.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.ttl.data.db.dao.CardDao
import de.niku.ttl.data.db.dao.CardSetDao
import de.niku.ttl.data.db.dao.CardSetWithCardsDao
import de.niku.ttl.data.db.dao.LearnStatDao
import de.niku.ttl.data.fs.FsRepo
import de.niku.ttl.data.fs.FsRepoImpl
import de.niku.ttl.data.repo.card_set.CardSetRepo
import de.niku.ttl.data.repo.card_set.CardSetRepoImpl
import javax.inject.Singleton

@Module
class DataRepoModule {

    @Provides
    @Singleton
    fun provideCardSetRepo(cardSetDao: CardSetDao,
                           cardDao: CardDao,
                           cardSetWithCardsDao: CardSetWithCardsDao,
                           learnStatDao: LearnStatDao) : CardSetRepo {
        return CardSetRepoImpl(
            cardSetDao,
            cardDao,
            cardSetWithCardsDao,
            learnStatDao
        )
    }

    @Provides
    @Singleton
    fun provideFsRepo(context: Context) : FsRepo {
        return FsRepoImpl(context)
    }
}