package com.divao.mesanews.presentation.common

import android.os.Bundle
import com.divao.mesanews.presentation.common.livedata.StateEventLiveData
import com.evernote.android.state.Bundler
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
class BundlerLiveData<T : Serializable> : Bundler<StateEventLiveData<T>> {
    override fun put(key: String, value: StateEventLiveData<T>, bundle: Bundle) {
        value.value?.let { bundle.putSerializable(key, it) }
    }

    override fun get(key: String, bundle: Bundle): StateEventLiveData<T> {
        val liveData = StateEventLiveData<T>()

        val value = bundle.getSerializable(key)
        value?.let { liveData.value = it as? StateEvent<T> }

        return liveData
    }
}