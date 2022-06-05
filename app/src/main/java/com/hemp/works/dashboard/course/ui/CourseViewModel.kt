package com.hemp.works.dashboard.course.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.course.data.repository.CourseRepository
import com.hemp.works.dashboard.model.Course
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseViewModel @Inject constructor(private val repository: CourseRepository): BaseViewModel(repository) {

    init {
        fetchCourseList()
    }

    private val _courseList: MutableLiveData<List<Course>> = Transformations.map(repository.courseList) {
        filterCourseList(it)
    } as MutableLiveData<List<Course>>
    val courseList: LiveData<List<Course>> = _courseList

    private fun filterCourseList(list: List<Course>) : List<Course> {
        return if (!searchText.isNullOrBlank()) {
            list.filter { newsLetter ->
                newsLetter.title?.contains(searchText!!, true) == true
            }
        } else {
            list
        }
    }

    private fun fetchCourseList() {
        viewModelScope.launch {
            repository.fetchCourses()
        }
    }

    val booleanResponse = repository.booleanResponse

    fun applyForCourses() {
        viewModelScope.launch {
            repository.applyForCourses()
        }
    }

    fun updateSearchTextForCourses(text: String?) {
        searchText = text
        _courseList.postValue(filterCourseList(repository.courseList.value!!))
    }

    private val _listVisibility= MutableLiveData(false)
    val listVisibility: LiveData<Boolean> = _listVisibility

    private var searchText: String? = null

    fun handleListVisibility(isEmpty: Boolean) {
        _listVisibility.postValue(!isEmpty)
    }

    fun clearSearchText() {
        searchText = null
    }

}