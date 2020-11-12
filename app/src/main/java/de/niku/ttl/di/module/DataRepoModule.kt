package de.niku.ttl.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.ttl.data.db.dao.*
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
                           questionDao: QuestionDao,
                           cardSetWithCardsDao: CardSetWithCardsDao,
                           learnStatDao: LearnStatDao) : CardSetRepo {
        return CardSetRepoImpl(
            cardSetDao,
            cardDao,
            questionDao,
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