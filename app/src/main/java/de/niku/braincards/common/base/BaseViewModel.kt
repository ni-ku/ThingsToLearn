package de.niku.braincards.common.base

import androidx.lifecycle.ViewModel
import de.niku.braincards.common.live_data.SingleLiveEvent

abstract class BaseViewModel<E> : ViewModel() {

    var mEvents: SingleLiveEvent<E> = SingleLiveEvent()
}