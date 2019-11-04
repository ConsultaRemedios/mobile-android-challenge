package com.example.mobile_android_challenge

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

interface SchedulerProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}

class AppSchedulerProvider : SchedulerProvider {
    override fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}

@Module()
class SchedulerModule {
    @Provides
    @Singleton
    fun providesAppScheduler(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}