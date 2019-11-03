package de.niku.ttl.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.ttl.data.db.BrainCardsDb
import de.niku.ttl.data.db.dao.CardDao
import de.niku.ttl.data.db.dao.CardSetDao
import de.niku.ttl.data.db.dao.CardSetWithCardsDao
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
}