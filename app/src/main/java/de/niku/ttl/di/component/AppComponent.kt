package de.niku.ttl.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import de.niku.ttl.TtlApplication
import de.niku.ttl.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    FragmentModule::class,
    DataRepoModule::class,
    DbModule::class,
    DialogModule::class,
    AppModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TtlApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: TtlApplication)
}