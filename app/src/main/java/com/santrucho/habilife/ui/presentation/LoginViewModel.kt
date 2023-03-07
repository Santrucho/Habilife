package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    var emailValue : MutableState<String> = mutableStateOf("")
    var isEmailValid: MutableState<Boolean> = mutableStateOf(false)
    var emailErrMsg: MutableState<String> = mutableStateOf("")

    var passwordValue: MutableState<String> = mutableStateOf("")
    var isPasswordValid : MutableState<Boolean> = mutableStateOf(false)
    var passwordErrMsg : MutableState<String> = mutableStateOf("")
    var passwordVisibility: MutableState<Boolean> = mutableStateOf(false)


    private var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow : StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            emailErrMsg.value.isEmpty()
                    && emailValue.value.isNotBlank()
                    && passwordValue.value.isNotBlank()
    }
    //Reset the fields values
    fun resetValues(){
        emailValue.value = ""
        passwordValue.value = ""
    }
    //If the user is register and its already login, initialize the app and go to the home screen directly
    init{
        if(repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    //Call the repository and sign in
    fun login(email:String,password:String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading()
        val resultData = repository.loginUser(email, password)
        _loginFlow.value = resultData
    }
    //Sign out the user
    fun logout(){
        repository.logout()
        _loginFlow.value = null
    }
}