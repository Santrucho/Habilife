package com.santrucho.habilife.ui.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.repository.SignUpRepository
import com.santrucho.habilife.ui.data.repository.login.LoginRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository:LoginRepository) : ViewModel() {

    var userError: String = ""

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow : StateFlow<Resource<FirebaseUser>?> = _loginFlow

    val currentUser : FirebaseUser?
        get() = repository.currentUser

    /*init{
        if(repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }*/

    fun login(email:String,password:String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading()
        val resultData = repository.loginUser(email, password)
        _loginFlow.value = resultData
    }
}