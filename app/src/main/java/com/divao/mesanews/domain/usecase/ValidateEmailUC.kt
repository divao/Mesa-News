package com.divao.mesanews.domain.usecase

import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.exception.InvalidFormFieldException
import com.divao.mesanews.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmailUC @Inject constructor(
    @BackgroundScheduler executorScheduler: Scheduler,
    @MainScheduler postExecutionScheduler: Scheduler,
    logger: Logger
) : CompletableUseCase<ValidateEmailUC.Request>(executorScheduler, postExecutionScheduler, logger) {
    override fun getRawCompletable(params: Request): Completable = Completable.fromAction {
        if (!Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(params.email).matches()
        ) {
            throw InvalidFormFieldException()
        }
    }

    data class Request(val email: String)
}