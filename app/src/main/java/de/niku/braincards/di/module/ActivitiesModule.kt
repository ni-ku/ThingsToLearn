package de.niku.braincards.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.braincards.view.activity_main.MainActivity
import de.niku.braincards.view.activity_main.MainActivityModule

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class
    ])
    abstract fun contributeMainActivity(): MainActivity
}