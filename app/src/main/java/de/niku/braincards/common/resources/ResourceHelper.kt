package de.niku.braincards.common.resources

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ResourceHelper(
    var context: Context?
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        context = null
    }

    fun getString(id: Int): String? {
        return context?.getString(id)
    }

}