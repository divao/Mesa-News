package com.divao.mesanews.presentation.scene.signup

import com.divao.mesanews.common.di.PerScene
import com.divao.mesanews.presentation.common.navigation.FlowComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class SignUpModule(private val view: SignUpView) {

    @Provides
    @PerScene
    fun provideSignUpView() = view
}

@Component(modules = [SignUpModule::class], dependencies = [FlowComponent::class])
@PerScene
interface SignUpComponent {
    fun inject(signUpFragment: SignUpFragment)
}