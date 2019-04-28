package de.niku.braincards.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.niku.braincards.BrainCardsApplication
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: BrainCardsApplication): Context {
        return app.applicationContext
    }
}