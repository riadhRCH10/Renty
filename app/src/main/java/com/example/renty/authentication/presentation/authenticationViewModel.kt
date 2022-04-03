package com.example.renty.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.renty.authentication.data.prefs.PrefsStore
import com.example.renty.authentication.domain.model.loginRequest
import com.example.renty.authentication.domain.model.phoneVerificationRequest
import com.example.renty.authentication.domain.model.registerRequest
import com.example.renty.authentication.domain.use_case.LogInUseCase
import com.example.renty.authentication.domain.use_case.PhoneVerificationUseCase
import com.example.renty.authentication.domain.use_case.RegisterUseCase
import com.example.renty.core.utils.AuthenticationEvent
import com.example.renty.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class authenticationViewModel @Inject constructor(
    private val loginUseCase: LogInUseCase,
    private val phoneVerificationUseCase: PhoneVerificationUseCase,
    private val registerUseCase: RegisterUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {

    private val _authEvents = MutableStateFlow<AuthenticationEvent>(AuthenticationEvent.Empty)
    val authEvents: StateFlow<AuthenticationEvent> = _authEvents


    fun login(Phone: String, Password: String) {
        val body = loginRequest(
            phone_number = Phone,
            password = Password
        )
        viewModelScope.launch {
            loginUseCase(body)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authEvents.value = AuthenticationEvent.Loading
                        }
                        is Resource.Success -> {
                            _authEvents.value =
                                AuthenticationEvent.Success("logged in ! ${result.data} ",result.data)
                            val token = result.data!!.token.token
                            setId(token)


                        }
                        is Resource.Error -> {
                            _authEvents.value = AuthenticationEvent.Failure(
                                "failed to log in : ${result.message}",
                                result.errorCode
                            )
                        }
                    }

                }.launchIn(this)
        }
    }

    fun phoneVerification(Phone: String) {
        val body = phoneVerificationRequest(phone_number = Phone)
        viewModelScope.launch {
            phoneVerificationUseCase(body)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authEvents.value = AuthenticationEvent.Loading
                        }
                        is Resource.Success -> {
                            _authEvents.value =
                                AuthenticationEvent.Success("verified ! ${result.data} ")
                        }
                        is Resource.Error -> {
                            _authEvents.value = AuthenticationEvent.Failure(
                                "failed verify : ${result.message}",
                                result.errorCode
                            )
                        }
                    }

                }.launchIn(this)
        }

    }

    fun setId(id: String) {
        viewModelScope.launch {
            prefsStore.setId(id)
        }
    }

    fun register(Phone: String, Name: String, LastName: String, Email: String, Password: String) {

        val body = registerRequest(
            phone_number = Phone,
            first_name = Name,
            last_name = LastName,
            email = Email,
            password = Password
        )
        viewModelScope.launch {
            registerUseCase(body)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authEvents.value = AuthenticationEvent.Loading
                        }
                        is Resource.Success -> {
                            _authEvents.value = AuthenticationEvent.Success("registered !")

                        }
                        is Resource.Error -> {
                            _authEvents.value = AuthenticationEvent.Failure(
                                "failed to register : ${result.message}",
                                result.errorCode
                            )
                        }
                    }
                }.launchIn(this)
        }


    }

    //helper function
    fun restartEvents() {
        _authEvents.value = AuthenticationEvent.Empty
    }


}