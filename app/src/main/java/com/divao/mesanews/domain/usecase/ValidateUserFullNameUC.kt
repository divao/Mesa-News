package com.divao.mesanews.domain.usecase

import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.exception.InvalidFormFieldException
import com.divao.mesanews.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class ValidateUserFullNameUC @Inject constructor(
    @BackgroundScheduler executorScheduler: Scheduler,
    @MainScheduler postExecutionScheduler: Scheduler,
    logger: Logger
) : CompletableUseCase<ValidateUserFullNameUC.Request>(
    executorScheduler,
    postExecutionScheduler,
    logger
) {
    override fun getRawCompletable(params: Request): Completable = Completable.fromAction {
        val words = params.fullName.trim().split(" ")

        words.forEach { word ->
            if (word.isEmpty()) {
                throw InvalidFormFieldException()
            }
        }

        if (words.size <= 1 || !params.fullName.matches(Regex("^[\\p{L} .'-]+$"))) {
            throw InvalidFormFieldException()
        }
    }

    data class Request(val fullName: String)
}