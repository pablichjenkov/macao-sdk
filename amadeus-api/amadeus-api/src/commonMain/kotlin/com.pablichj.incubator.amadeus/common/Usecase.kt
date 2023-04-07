package com.pablichj.incubator.amadeus.common

import kotlinx.coroutines.flow.Flow


interface SingleUseCase<in P, out R> {
    suspend fun doWork(params: P): R
}

interface SingleUseCaseNoParam<out R> {
    suspend fun doWork(): R
}

interface FlowUseCase<in P, out R> {
    suspend fun doWork(params: P): Flow<R>
}
