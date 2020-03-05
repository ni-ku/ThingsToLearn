package de.niku.ttl.common.base

import androidx.lifecycle.MutableLiveData

class ViewState {

    var state: MutableLiveData<Int>

    constructor() {
        state = MutableLiveData()
        state.value = CONTENT
    }

    companion object {
        const val CONTENT = 1
        const val EMPTY = 2
    }
}