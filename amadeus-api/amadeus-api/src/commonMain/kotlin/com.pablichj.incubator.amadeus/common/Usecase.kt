package com.pablichj.incubator.amadeus.common

import kotlinx.coroutines.flow.Flow


interface SingleUseCase<in P, out R> {
    suspend fun doWork(params: P): R
}

interface SingleUseCaseSource<out R> {
    suspend fun doWork(): R
}

interface FlowUseCase<in P, out R> {
    suspend fun doWork(params: P): Flow<R>
}

interface FlowUseCaseSource<out R> {
    suspend fun doWork(): Flow<R>
}
