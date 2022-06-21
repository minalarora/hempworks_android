package com.hemp.works.dashboard.course.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.course.data.remote.CourseRemoteDataSource
import com.hemp.works.dashboard.model.Course
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(private val remoteDataSource: CourseRemoteDataSource) : BaseRepository() {

    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>>
        get() = _courseList

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    suspend fun fetchCourses() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getCourses()}?.let {
            it.data?.let { list -> _courseList.postValue(list) }
        }
    }

    suspend fun applyForCourses() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.applyForCourses()
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

}