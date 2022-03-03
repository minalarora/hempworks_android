package com.hemp.works.dashboard.tnl.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.LiveSession
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.model.Tutorial
import com.hemp.works.dashboard.tnl.data.repository.TNLRepository
import kotlinx.coroutines.launch
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class TNLViewModel @Inject constructor(private val repository: TNLRepository): BaseViewModel(repository) {

    private val _tutorialList: MutableLiveData<List<Tutorial>> = Transformations.map(repository.tutorialList) {
        filterTutorialList(it)
    } as MutableLiveData<List<Tutorial>>
    val tutorialList: LiveData<List<Tutorial>> = _tutorialList

    private fun filterTutorialList(list: List<Tutorial>): List<Tutorial> {
        return if (!searchText.isNullOrBlank()) {
            list.filter { tutorial ->
                tutorial.description?.contains(searchText!!, true) == true
            }
        } else {
            list
        }
    }

    fun fetchTutorialList() {
        viewModelScope.launch {
            repository.fetchTutorials()
        }
    }

    fun updateSearchTextForTutorial(text: String?) {
        searchText = text
        _tutorialList.postValue(filterTutorialList(repository.tutorialList.value!!))
    }

    private val _newsLetterList: MutableLiveData<List<NewsLetter>> = Transformations.map(repository.newsLetterList) {
        filterNewsLetterList(it)
    } as MutableLiveData<List<NewsLetter>>
    val newsLetterList: LiveData<List<NewsLetter>> = _newsLetterList

    private fun filterNewsLetterList(list: List<NewsLetter>) : List<NewsLetter> {
        return if (!searchText.isNullOrBlank()) {
            list.filter { newsLetter ->
                newsLetter.title?.contains(searchText!!, true) == true
            }
        } else {
            list
        }
    }

    fun fetchNewsLetterList() {
        viewModelScope.launch {
            repository.fetchNewsLetter()
        }
    }

    fun updateSearchTextForNewsLetter(text: String?) {
        searchText = text
        _newsLetterList.postValue(filterNewsLetterList(repository.newsLetterList.value!!))
    }

    private val _liveSessionList: MutableLiveData<List<LiveSession>> = Transformations.map(repository.liveSessionList) {
        filterLiveSessionList(it)
    } as MutableLiveData<List<LiveSession>>
    val liveSessionList: LiveData<List<LiveSession>> = _liveSessionList

    private fun filterLiveSessionList(list: List<LiveSession>) : List<LiveSession> {
        return if (!searchText.isNullOrBlank()) {
            list.filter { liveSession ->
                liveSession.title?.contains(searchText!!, true) == true
            }
        } else {
            list
        }
    }

    fun fetchLiveSessionList() {
        viewModelScope.launch {
            repository.fetchLiveSessions()
        }
    }

    fun updateSearchTextForLiveSession(text: String?) {
        searchText = text
        _liveSessionList.postValue(filterLiveSessionList(repository.liveSessionList.value!!))
    }

    private val _listVisibility= MutableLiveData(false)
    val listVisibility: LiveData<Boolean> = _listVisibility

    private val _titleText= MutableLiveData("")
    val titleText: LiveData<String> = _titleText

    private var searchText: String? = null
    lateinit var fragmentType: TNLType

    fun handleListVisibility(isEmpty: Boolean) {
        _listVisibility.postValue(!isEmpty)
    }

    fun clearSearchText() {
        searchText = null
    }

    fun updateFragmentType(type: TNLType) {
        fragmentType = type
        _titleText.postValue(when(fragmentType) {
            TNLType.TUTORIAL -> "Tutorial"
            TNLType.NEWSLETTER -> "NewsLetter"
            TNLType.LIVESESSION -> "Live Session"
        })
    }
}

enum class TNLType(val type: String) {
    TUTORIAL("tutorial"), NEWSLETTER("newsletter"), LIVESESSION("livesession");

    companion object {
        fun getTNLTypeFromString (type: String) = when(type) {
                TUTORIAL.type -> TUTORIAL
                NEWSLETTER.type -> NEWSLETTER
                LIVESESSION.type -> LIVESESSION
                else -> throw UnsupportedOperationException()
        }
    }
}
