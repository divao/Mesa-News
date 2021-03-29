package com.divao.mesanews.presentation.scene.signup

data class PasswordConfirmationInputValidationVM(
    val password: String,
    val passwordConfirmation: String
)

data class SignUpInputVM(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
)