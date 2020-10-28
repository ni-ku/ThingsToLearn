package de.niku.ttl.common.base

import androidx.lifecycle.MutableLiveData

class ViewState {

    var state: MutableLiveData<Int> = MutableLiveData()

    init {
        state.value = CONTENT
    }

    companion object {
        const val CONTENT = 1
        const val EMPTY = 2
    }
}