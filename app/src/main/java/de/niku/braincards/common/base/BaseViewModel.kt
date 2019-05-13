package de.niku.braincards.common.base

import androidx.lifecycle.ViewModel
import de.niku.braincards.common.resources.ResourceHelper
import de.niku.braincards.common.rxjava.SingleLiveEvent

abstract class BaseViewModel<E> : ViewModel() {

    var mEvents: SingleLiveEvent<E> = SingleLiveEvent()
    lateinit var mResHelper: ResourceHelper
}