package com.divao.mesanews.domain.usecase

import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.exception.InvalidFormFieldException
import com.divao.mesanews.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class ValidatePasswordUC @Inject constructor(
    @BackgroundScheduler executorScheduler: Scheduler,
    @MainScheduler postExecutionScheduler: Scheduler,
    logger: Logger
) : CompletableUseCase<ValidatePasswordUC.Request>(
    executorScheduler,
    postExecutionScheduler,
    logger
) {
    override fun getRawCompletable(params: Request): Completable = Completable.fromAction {
        if (params.password.length < 8) {
            throw InvalidFormFieldException()
        }
    }

    data class Request(val password: String)
}