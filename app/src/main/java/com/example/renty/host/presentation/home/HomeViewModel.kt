package com.example.renty.host.presentation.home

import androidx.lifecycle.*
import com.example.renty.authentication.data.prefs.PrefsStore
import com.example.renty.core.utils.Resource
import com.example.renty.homeResident.domain.use_case.GetInformationsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getinfoUseCase : GetInformationsUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {

    private val _role = MutableLiveData<Int>()
    val role : LiveData<Int> = _role

    val userFlowId = prefsStore.getId()
    val userId = prefsStore.getId().asLiveData()

    fun getInfos() {
        viewModelScope.launch {
            userFlowId.collect { result ->
                result?.let {
                    getinfoUseCase(it)
                        .onEach { result ->
                            when (result) {
                                is Resource.Loading -> {

                                }
                                is Resource.Error -> {

                                }
                                is Resource.Success -> {
                                    _role.value = result.data?.user?.user_type

                                }
                            }

                        }.launchIn(this)
                }
            }
        }
    }
}