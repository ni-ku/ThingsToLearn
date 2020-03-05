package de.niku.ttl.common.base

import androidx.lifecycle.ViewModel
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.common.rxjava.SingleLiveEvent

abstract class BaseViewModel<E> : ViewModel() {

    var mEvents: SingleLiveEvent<E> = SingleLiveEvent()
    lateinit var mResHelper: ResourceHelper
}