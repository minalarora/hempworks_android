package com.hemp.works.dashboard.search.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.search.data.repository.SearchRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository) : BaseViewModel(repository) {
}