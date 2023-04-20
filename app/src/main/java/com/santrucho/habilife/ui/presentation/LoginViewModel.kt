package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.domain.login.GetCurrentUserUseCase
import com.santrucho.habilife.ui.domain.login.LogOutUseCase
import com.santrucho.habilife.ui.domain.login.LoginUseCase
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    var emailValue: MutableState<String> = mutableStateOf("")
    var isEmailValid: MutableState<Boolean> = mutableStateOf(false)
    var emailErrMsg: MutableState<String> = mutableStateOf("")

    var passwordValue: MutableState<String> = mutableStateOf("")
    var isPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    var passwordErrMsg: MutableState<String> = mutableStateOf("")
    var passwordVisibility: MutableState<Boolean> = mutableStateOf(false)


    private var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private var _currentUser = MutableStateFlow<Resource<User>?>(null)
    val currentUser: StateFlow<Resource<User>?> = _currentUser

    private val _loginFlow = MutableStateFlow<Resource<User>?>(null)
    val loginFlow: StateFlow<Resource<User>?> = _loginFlow

    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            emailErrMsg.value.isEmpty()
                    && emailValue.value.isNotBlank()
                    && passwordValue.value.isNotBlank()
    }

    //Reset the fields values
    private fun resetValues() {
        emailValue.value = ""
        passwordValue.value = ""
    }

    //If the user is register and its already login, initialize the app and go to the home screen directly
    init {
        viewModelScope.launch {
            getCurrentUserUseCase().let { resource ->
                _currentUser.value = resource
                _loginFlow.value = resource
            }
        }
    }

    fun currentUser() {
        viewModelScope.launch {
            _currentUser.value = Resource.Loading()
            _currentUser.value = getCurrentUserUseCase()
        }
    }

    //Call the repository and sign in
    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading()
        val result = loginUseCase(email, password)
        _loginFlow.value = result
        _currentUser.value = getCurrentUserUseCase()
        resetValues()
    }

    //Sign out the user
    fun logout() {
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading()
            logOutUseCase()
            _loginFlow.value = null
        }
    }
}