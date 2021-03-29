package com.divao.mesanews.domain.usecase

import com.divao.mesanews.domain.datarepository.NewsDataRepository
import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.model.News
import com.divao.mesanews.domain.utility.Logger
import io.reactivex.Scheduler
import javax.inject.Inject

class GetNewsListUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    logger: Logger,
    private val newsRepository: NewsDataRepository
) : SingleUseCase<List<News>, Unit>(backgroundScheduler, mainScheduler, logger) {

    override fun getRawSingle(params: Unit) = newsRepository.getNewsList()
}