package com.hemp.works.dashboard.calculator.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.calculator.data.remote.CalculatorRemoteDataSource
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculatorRepository  @Inject constructor(private val remoteDataSource: CalculatorRemoteDataSource) : BaseRepository() {
}