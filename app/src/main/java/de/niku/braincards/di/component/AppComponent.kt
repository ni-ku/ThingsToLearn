package de.niku.braincards.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import de.niku.braincards.BrainCardsApplication
import de.niku.braincards.di.module.ActivitiesModule
import de.niku.braincards.di.module.DataRepoModule
import de.niku.braincards.di.module.FragmentModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    FragmentModule::class,
    DataRepoModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BrainCardsApplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: BrainCardsApplication)
}