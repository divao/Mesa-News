package com.divao.mesanews.domain.usecase

import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.exception.InvalidFormFieldException
import com.divao.mesanews.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class ValidatePasswordConfirmationUC @Inject constructor(
    @BackgroundScheduler executorScheduler: Scheduler,
    @MainScheduler postExecutionScheduler: Scheduler,
    logger: Logger
) : CompletableUseCase<ValidatePasswordConfirmationUC.Request>(
    executorScheduler,
    postExecutionScheduler,
    logger
) {
    override fun getRawCompletable(params: Request): Completable =
        if (params.password == params.passwordConfirmation) Completable.complete() else Completable.error(
            InvalidFormFieldException()
        )

    data class Request(
        val password: String,
        val passwordConfirmation: String
    )
}