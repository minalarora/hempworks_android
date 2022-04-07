package com.hemp.works.dashboard.support.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.model.Chat
import com.hemp.works.dashboard.model.Message
import com.hemp.works.dashboard.support.data.remote.SupportRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class SupportRepository @Inject constructor(private val remoteDataSource: SupportRemoteDataSource) : BaseRepository() {

    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat>
        get() = _chat

    lateinit var identifierId: String

    suspend fun initChat(id: String) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.initChat(id) }?.let {
            it.data?.let { chat -> _chat.postValue(chat) }
            identifierId = id
            fetchChat(id)
        }
    }

    private suspend fun fetchChat(id: String) {
        while (true) {
            delay(5000)
            val response = getResult(error = Constants.CHAT_ERROR, handleLoading = false){ remoteDataSource.initChat(id) }
            response?.data?.let { chat -> _chat.postValue(chat) }
        }
    }

    suspend fun sendMessage(message: Message) {
        _chat.value?.let {
            it.messages?.add(message)
            _chat.postValue(it)
        }
        val response = getResult(error = Constants.CHAT_ERROR, handleLoading = false){ remoteDataSource.sendMessage(chat.value?.id!!,message.message!!) }
        if (response == null) {
            message.isSent.set(false)
        } else {
            response.data?.let { chat -> _chat.postValue(chat) }
        }
    }


    suspend fun retryMessage() {
        _chat.value?.let {
            val unSendMessages = it.messages?.filter { !it.isSent.get() }
            for (message in unSendMessages ?: emptyList()) {
                message.isSent.set(true)
                val response = getResult(
                    error = Constants.CHAT_ERROR,
                    handleLoading = false
                ) {
                    remoteDataSource.sendMessage(chat.value?.id!!, message.message!!)
                }
                if (response == null) {
                    message.isSent.set(false)
                } else {
                    response.data?.let { chat -> _chat.postValue(chat) }
                }
            }
        }

    }
}