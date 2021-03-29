package com.divao.mesanews.presentation.common

import java.io.Serializable


data class TextInputStateVM(
    val value: String,
    val status: InputStatusVM
) : Serializable

enum class InputStatusVM {
    EMPTY, INVALID, VALID, UNDEFINED
}