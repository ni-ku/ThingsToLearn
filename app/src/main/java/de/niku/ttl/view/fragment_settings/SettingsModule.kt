package de.niku.ttl.view.fragment_settings

import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsViewModel(): SettingsViewModel {
        return SettingsViewModel()
    }
}