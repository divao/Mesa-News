package com.divao.mesanews.presentation.common

import android.text.Editable
import android.widget.EditText
import com.divao.mesanews.domain.exception.EmptyRequiredFieldException
import com.divao.mesanews.domain.exception.FieldValidationException
import com.jakewharton.rxbinding3.view.focusChanges
import io.reactivex.Completable
import io.reactivex.Observable

fun EditText.focusLosses(): Observable<String> = this.focusChanges().skipInitialValue().filter { !it }.map { textString }

fun EditText.textChanges(): Observable<String> = this.textChanges().map { it.toString() }

fun EditText.setInputState(state: TextInputStateVM, invalidMessage: String? = null, emptyMessage: String? = null) {
    if (state.value != textString) {
        textString = state.value
    }
    error = when (state.status) {
        InputStatusVM.VALID -> null
        InputStatusVM.UNDEFINED -> null
        InputStatusVM.INVALID -> invalidMessage
        InputStatusVM.EMPTY -> emptyMessage
    }
}

var EditText.textString: String
    get() = text.toString()
    set(value) {
        this.text = Editable.Factory.getInstance().newEditable(value)
    }

fun Completable.toTextInputStateHandlerCompletable(value: String, stateHandler: (validationStatus: TextInputStateVM) -> Unit): Completable =
    this.doOnComplete { stateHandler(TextInputStateVM(value, InputStatusVM.VALID)) }.doOnError {
        if (it is FieldValidationException) {
            stateHandler(TextInputStateVM(value, it.validationStatus))
        }
    }

val FieldValidationException.validationStatus: InputStatusVM
    get() = when (this) {
        is EmptyRequiredFieldException -> InputStatusVM.EMPTY
        else -> InputStatusVM.INVALID
    }