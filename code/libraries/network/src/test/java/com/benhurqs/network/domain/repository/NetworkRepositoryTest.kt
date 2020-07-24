package com.benhurqs.network.domain.repository

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class NetworkRepositoryTest{

    private lateinit var service: APIServiceHelper
    private lateinit var testScheduler: Scheduler

    @Before
    fun setUp(){
        testScheduler = Schedulers.trampoline()
        service = APIServiceHelper()
    }

    @Test
    fun `Check if call callbacks when list is success`(){

    }

    private fun getRepository(type: APIServiceType): NetworkRepository{
       return NetworkRepository(service, testScheduler, testScheduler, type)
    }

}