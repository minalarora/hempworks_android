package com.hemp.works.dashboard.tnl.data.remote

import com.hemp.works.dashboard.model.LiveSession
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.model.Tutorial
import retrofit2.Response
import retrofit2.http.GET

interface TNLService {

    @GET("/v1/livesession")
    suspend fun getLiveSessions(): Response<List<LiveSession>>

    @GET("/v1/newspaper")
    suspend fun getNewsLetters(): Response<List<NewsLetter>>

    @GET("/v1/tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>


}