package com.divao.mesanews.presentation.scene.signup

import com.divao.mesanews.presentation.common.TextInputStateVM
import io.reactivex.Observable

interface SignUpView {

    val onViewLoaded: Observable<Unit>

    val onNameInputFocusLost: Observable<String>
    val onEmailInputFocusLost: Observable<String>
    val onPasswordInputFocusLost: Observable<String>
    val onPasswordConfirmationInputFocusLost: Observable<PasswordConfirmationInputValidationVM>
    val onSignUp: Observable<SignUpInputVM>

    fun displayNameInputValidationStatus(status: TextInputStateVM)
    fun displayEmailInputValidationStatus(status: TextInputStateVM)
    fun displayPasswordInputValidationStatus(status: TextInputStateVM)
    fun displayPasswordConfirmationInputValidationStatus(status: TextInputStateVM)
}