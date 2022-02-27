package com.hemp.works.dashboard.tnl.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.LiveSession
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.model.Tutorial
import com.hemp.works.dashboard.tnl.data.remote.TNLRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TNLRepository @Inject constructor(private val remoteDataSource: TNLRemoteDataSource) : BaseRepository(){

    private val _tutorialList = MutableLiveData<List<Tutorial>>()
    val tutorialList: LiveData<List<Tutorial>>
        get() = _tutorialList

    private val _liveSessionList = MutableLiveData<List<LiveSession>>()
    val liveSessionList: LiveData<List<LiveSession>>
        get() = _liveSessionList

    private val _newsLetterList = MutableLiveData<List<NewsLetter>>()
    val newsLetterList: LiveData<List<NewsLetter>>
        get() = _newsLetterList

    suspend fun fetchTutorials() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.getTutorials()}?.let {
            it.data?.let { list -> _tutorialList.postValue(list) }
        }
    }

    suspend fun fetchLiveSessions() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.getLiveSessions()}?.let {
            it.data?.let { list -> _liveSessionList.postValue(list) }
        }
    }

    suspend fun fetchNewsLetter() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.getNewsLetters()}?.let {
            it.data?.let { list -> _newsLetterList.postValue(list) }
        }
    }
}