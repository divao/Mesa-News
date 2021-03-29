package com.divao.mesanews.presentation.scene.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.divao.mesanews.R
import com.divao.mesanews.presentation.common.*
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SignUpFragment : Fragment(), SignUpView {

    private val disposeBag = CompositeDisposable()

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }

    @Inject
    lateinit var presenter: SignUpPresenter

    @Inject
    lateinit var router: Router

    override val onViewLoaded: PublishSubject<Unit> = PublishSubject.create()

    override val onNameInputFocusLost: PublishSubject<String> = PublishSubject.create()
    override val onEmailInputFocusLost: PublishSubject<String> = PublishSubject.create()
    override val onPasswordInputFocusLost: PublishSubject<String> = PublishSubject.create()
    override val onPasswordConfirmationInputFocusLost: PublishSubject<PasswordConfirmationInputValidationVM> =
        PublishSubject.create()
    override val onSignUp: PublishSubject<SignUpInputVM> = PublishSubject.create()

    fun loadViews() {
        nameEditText.focusLosses().subscribe(onNameInputFocusLost)
        emailEditText.focusLosses().subscribe(onEmailInputFocusLost)
        passwordEditText.focusLosses().subscribe(onPasswordInputFocusLost)
        passwordConfirmationEditText.focusLosses().map {
            PasswordConfirmationInputValidationVM(
                passwordEditText.textString,
                passwordConfirmationEditText.textString
            )
        }.subscribe(onPasswordConfirmationInputFocusLost)

        signUpButton.clicks().map {
            SignUpInputVM(
                nameEditText.textString,
                emailEditText.textString,
                passwordEditText.textString,
                passwordConfirmationEditText.textString
            )
        }.subscribe(onSignUp)
    }

    override fun displayNameInputValidationStatus(status: TextInputStateVM) {
        nameEditText.setInputState(status, activity?.getString(R.string.invalid_input_error))
    }

    override fun displayEmailInputValidationStatus(status: TextInputStateVM) {
        emailEditText.setInputState(status, activity?.getString(R.string.invalid_input_error))
    }

    override fun displayPasswordInputValidationStatus(status: TextInputStateVM) {
        passwordEditText.setInputState(status, activity?.getString(R.string.invalid_input_error))
    }

    override fun displayPasswordConfirmationInputValidationStatus(status: TextInputStateVM) {
        passwordConfirmationEditText.setInputState(
            status,
            activity?.getString(R.string.invalid_input_error)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadViews()
        presenter.handleViewCreation(lifecycle)
        onViewLoaded.onNext(Unit)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            DaggerSignUpComponent.builder()
                .signUpModule(SignUpModule(this))
                .flowComponent((parentFragment as FlowContainerFragment).component)
                .build()
                .inject(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBag.clear()
        presenter.onViewDestroyed()
    }


}