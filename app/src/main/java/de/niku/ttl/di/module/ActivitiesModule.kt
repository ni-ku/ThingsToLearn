package de.niku.ttl.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.ttl.view.activity_main.MainActivity
import de.niku.ttl.view.activity_main.MainActivityModule

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class
    ])
    abstract fun contributeMainActivity(): MainActivity
}