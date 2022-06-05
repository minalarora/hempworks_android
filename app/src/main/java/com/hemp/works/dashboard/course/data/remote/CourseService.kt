package com.hemp.works.dashboard.course.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Course
import retrofit2.Response
import retrofit2.http.GET

interface CourseService {

    @GET("/v1/course")
    suspend fun getCourses(): Response<List<Course>>

    @GET("/v1/course/apply")
    suspend fun applyForCourses(): Response<BooleanResponse>

}