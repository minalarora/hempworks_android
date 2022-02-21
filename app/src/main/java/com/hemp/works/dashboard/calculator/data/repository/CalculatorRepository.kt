package com.hemp.works.dashboard.calculator.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.calculator.data.remote.CalculatorRemoteDataSource
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.model.CalculatorProduct
import com.hemp.works.dashboard.model.Prescription
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculatorRepository  @Inject constructor(private val remoteDataSource: CalculatorRemoteDataSource) : BaseRepository() {

    private val _calculatorList = MutableLiveData<List<CalculatorProduct>>()
    val calculatorList: LiveData<List<CalculatorProduct>>
        get() = _calculatorList

    suspend fun fetchCalculatorList() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.getCalculatorList()}?.let {
            it.data?.let { list -> _calculatorList.postValue(list) }
        }
    }

}