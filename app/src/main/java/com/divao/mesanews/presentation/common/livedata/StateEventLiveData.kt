package com.divao.mesanews.presentation.common.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.divao.mesanews.presentation.common.StateEvent
import java.io.Serializable

class StateEventLiveData<T : Serializable> : MutableLiveData<StateEvent<T>>() {
    fun postEvent(data: T) {
        super.postValue(StateEvent(data = data))
    }

    fun postUniqueEvent(data: T) {
        super.postValue(StateEvent(isSingle = true, data = data))
    }

    fun observeStateEvent(owner: Lifecycle, observer: Observer<in StateEvent<T>>) {
        this.observe({ owner }, { stateEvent ->
            if (stateEvent == null) {
                return@observe
            }
            if (stateEvent.isSingle) {
                observer.onChanged(stateEvent)
                value = null
            } else {
                observer.onChanged(stateEvent)
            }
        })
    }
}