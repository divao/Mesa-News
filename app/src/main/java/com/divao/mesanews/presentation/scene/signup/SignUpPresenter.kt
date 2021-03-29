package com.divao.mesanews.presentation.scene.signup

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.divao.mesanews.domain.usecase.ValidateEmailUC
import com.divao.mesanews.domain.usecase.ValidatePasswordConfirmationUC
import com.divao.mesanews.domain.usecase.ValidatePasswordUC
import com.divao.mesanews.domain.usecase.ValidateUserFullNameUC
import com.divao.mesanews.presentation.common.BundlerLiveData
import com.divao.mesanews.presentation.common.TextInputStateVM
import com.divao.mesanews.presentation.common.livedata.StateEventLiveData
import com.divao.mesanews.presentation.common.toTextInputStateHandlerCompletable
import com.evernote.android.state.State
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private val view: SignUpView,
    private val validateUserFullNameUC: ValidateUserFullNameUC,
    private val validateEmailUC: ValidateEmailUC,
    private val validatePasswordUC: ValidatePasswordUC,
    private val validatePasswordConfirmationUC: ValidatePasswordConfirmationUC
) {

    private val disposable = CompositeDisposable()

    @State(BundlerLiveData::class)
    var nameInputState: StateEventLiveData<TextInputStateVM> = StateEventLiveData()

    @State(BundlerLiveData::class)
    var emailInputState: StateEventLiveData<TextInputStateVM> = StateEventLiveData()

    @State(BundlerLiveData::class)
    var passwordInputState: StateEventLiveData<TextInputStateVM> = StateEventLiveData()

    @State(BundlerLiveData::class)
    var passwordConfirmationInputState: StateEventLiveData<TextInputStateVM> = StateEventLiveData()

    fun handleViewCreation(lifecycle: Lifecycle) {
        view.onViewLoaded.doOnNext {
            observeEvents()
            observeViewState(lifecycle)
        }.subscribe().addTo(disposable)
    }

    private fun observeEvents() {
        view.onNameInputFocusLost.flatMapCompletable {
            getValidateUserFullNameCompletable(it).onErrorComplete()
        }.subscribe().addTo(disposable)

        view.onEmailInputFocusLost.flatMapCompletable {
            getValidateEmailCompletable(it).onErrorComplete()
        }.subscribe().addTo(disposable)

        view.onPasswordInputFocusLost.flatMapCompletable {
            getValidatePasswordCompletable(it).onErrorComplete()
        }.subscribe().addTo(disposable)

        view.onPasswordConfirmationInputFocusLost.flatMapCompletable {
            getValidatePasswordConfirmationCompletable(
                it.password,
                it.passwordConfirmation
            ).onErrorComplete()
        }.subscribe().addTo(disposable)

        view.onSignUp.flatMapCompletable {
            Completable.mergeDelayError(
                listOf(
                    getValidateUserFullNameCompletable(it.name),
                    getValidateEmailCompletable(it.email),
                    getValidatePasswordCompletable(it.password),
                    getValidatePasswordConfirmationCompletable(it.password, it.passwordConfirmation)
                )
            )
                .onErrorComplete()
        }.subscribe().addTo(disposable)

    }

    private fun observeViewState(lifecycle: Lifecycle) {
        nameInputState.observeStateEvent(lifecycle, Observer {
            view.displayNameInputValidationStatus(it.data)
        })

        emailInputState.observeStateEvent(lifecycle, Observer {
            view.displayEmailInputValidationStatus(it.data)
        })

        passwordInputState.observeStateEvent(lifecycle, Observer {
            view.displayPasswordInputValidationStatus(it.data)
        })

        passwordConfirmationInputState.observeStateEvent(lifecycle, Observer {
            view.displayPasswordConfirmationInputValidationStatus(it.data)
        })
    }

    private fun getValidateUserFullNameCompletable(name: String) =
        validateUserFullNameUC.getCompletable(ValidateUserFullNameUC.Request(name))
            .toTextInputStateHandlerCompletable(name, nameInputState::postEvent)

    private fun getValidateEmailCompletable(email: String) =
        validateEmailUC.getCompletable(ValidateEmailUC.Request(email))
            .toTextInputStateHandlerCompletable(email, emailInputState::postEvent)

    private fun getValidatePasswordCompletable(password: String) =
        validatePasswordUC.getCompletable(ValidatePasswordUC.Request(password))
            .toTextInputStateHandlerCompletable(password, passwordInputState::postEvent)

    private fun getValidatePasswordConfirmationCompletable(
        password: String,
        passwordConfirmation: String
    ) =
        validatePasswordConfirmationUC.getCompletable(
            ValidatePasswordConfirmationUC.Request(
                password,
                passwordConfirmation
            )
        )
            .toTextInputStateHandlerCompletable(
                passwordConfirmation,
                passwordConfirmationInputState::postEvent
            )

    fun onViewDestroyed() {
        disposable.clear()
    }

}