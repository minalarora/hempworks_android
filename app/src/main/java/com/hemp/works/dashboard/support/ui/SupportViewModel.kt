package com.hemp.works.dashboard.support.ui

import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Message
import com.hemp.works.dashboard.support.data.repository.SupportRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SupportViewModel @Inject constructor(private val repository: SupportRepository) : BaseViewModel(repository)  {

 val chat = repository.chat

  fun initChat(id: String) {
      viewModelScope.launch {
          repository.initChat(id)
      }
  }

   fun sendMessage(text: String?) {
       if (text.isNullOrBlank()) return
       viewModelScope.launch {
           repository.sendMessage(Message(message = text, type = "TEXT", isDoctor = true, date = Date()))
       }
   }

    fun retryMessage(message: Message) {
        viewModelScope.launch {
            repository.retryMessage(message)
        }
    }



}