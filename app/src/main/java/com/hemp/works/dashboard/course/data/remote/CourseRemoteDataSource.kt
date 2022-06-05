package com.hemp.works.dashboard.course.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.tnl.data.remote.TNLService
import javax.inject.Inject

class CourseRemoteDataSource @Inject constructor(private val service: CourseService): BaseDataSource() {

    suspend fun getCourses() = getResult { service.getCourses() }

    suspend fun applyForCourses() = getResult { service.applyForCourses() }

}