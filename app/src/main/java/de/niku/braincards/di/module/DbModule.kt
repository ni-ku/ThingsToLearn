package de.niku.braincards.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.braincards.data.db.BrainCardsDb
import de.niku.braincards.data.db.dao.CardDao
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.db.dao.CardSetWithCardsDao
import de.niku.braincards.data.db.dao.QuestionsDao
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): BrainCardsDb? {
        return BrainCardsDb.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCardSetDao(db: BrainCardsDb?): CardSetDao {
        return db!!.CardSetDao()
    }

    @Provides
    @Singleton
    fun provideCardDao(db: BrainCardsDb?): CardDao {
        return db!!.CardDao()
    }

    @Provides
    @Singleton
    fun provideCardSetWithCardsDao(db: BrainCardsDb?): CardSetWithCardsDao {
        return db!!.CardSetWithCardsDao()
    }

    @Provides
    @Singleton
    fun provideQuestionsDao(db: BrainCardsDb?): QuestionsDao {
        return db!!.QuestionsDao()
    }
}