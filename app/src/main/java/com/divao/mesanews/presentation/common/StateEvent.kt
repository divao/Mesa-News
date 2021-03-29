package com.divao.mesanews.presentation.common

import java.io.Serializable

class StateEvent<out T : Serializable>(val isSingle: Boolean = false, val data: T) : Serializable