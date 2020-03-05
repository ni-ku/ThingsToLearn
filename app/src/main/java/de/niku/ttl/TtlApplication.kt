package de.niku.ttl

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import de.niku.ttl.di.component.DaggerAppComponent
import javax.inject.Inject

class TtlApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var daj: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return daj;
    }
}