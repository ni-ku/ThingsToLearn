package de.niku.braincards.view.activity_main

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel() : MainViewModel {
        return MainViewModel()
    }
}