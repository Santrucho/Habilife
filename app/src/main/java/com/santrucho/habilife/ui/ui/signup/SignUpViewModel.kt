package com.santrucho.habilife.ui.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.repository.SignUpRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository:SignUpRepository) : ViewModel() {

    var userError: String = ""

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow : StateFlow<Resource<FirebaseUser>?> = _signUpFlow

    val currentUser : FirebaseUser?
        get() = repository.currentUser

    /*init{
        if(repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }*/

    fun signUp(username:String,email:String,password:String) = viewModelScope.launch {
        _signUpFlow.value = Resource.Loading()
        val resultData = repository.createUser(username,email, password)
        _signUpFlow.value = resultData
    }


}