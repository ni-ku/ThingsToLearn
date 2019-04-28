package de.niku.braincards.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.braincards.data.db.BrainCardsDb
import de.niku.braincards.data.db.dao.CardSetDao
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): BrainCardsDb? {
        return BrainCardsDb.getAppDb(context)
    }

    @Provides
    @Singleton
    fun provideCardSetDao(db: BrainCardsDb?): CardSetDao {
        return db!!.CardSetDao()
    }
}