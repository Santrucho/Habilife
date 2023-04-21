package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.remote.signup.SignUpRepository
import com.santrucho.habilife.ui.domain.signup.AddUserUseCase
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val addUserUseCase: AddUserUseCase) : ViewModel() {

    var usernameValue: MutableState<String> = mutableStateOf("")
    var isUsernameValid: MutableState<Boolean> = mutableStateOf(false)
    var usernameErrMsg: MutableState<String> = mutableStateOf("")

    var emailValue: MutableState<String> = mutableStateOf("")
    var isEmailValid: MutableState<Boolean> = mutableStateOf(false)
    var emailErrMsg: MutableState<String> = mutableStateOf("")

    var passwordValue: MutableState<String> = mutableStateOf("")
    var isPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    var passwordErrMsg: MutableState<String> = mutableStateOf("")
    var passwordVisibility: MutableState<Boolean> = mutableStateOf(false)

    var confirmPasswordValue: MutableState<String> = mutableStateOf("")
    var isConfirmPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    var confirmPasswordVisibility: MutableState<Boolean> = mutableStateOf(false)
    var confirmPasswordErrMsg: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow: StateFlow<Resource<FirebaseUser>?> = _signUpFlow


    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            usernameErrMsg.value.isEmpty()
                    &&
        emailErrMsg.value.isEmpty()
                    &&
        passwordErrMsg.value.isEmpty()
                    &&
        confirmPasswordErrMsg.value.isEmpty()
                && usernameValue.value.isNotBlank()
                && emailValue.value.isNotBlank()
                && passwordValue.value.isNotBlank()
                && confirmPasswordValue.value.isNotBlank()
    }

    fun validateUsername() {
        if (usernameValue.value.length <= 2) {
            isUsernameValid.value = true
            usernameErrMsg.value = "Username needs to have more than 2 chars"
        } else {
            isUsernameValid.value = false
            usernameErrMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    fun validateEmail() {
        val emailRegex = Regex("^[^\\p{Cc}\\p{Z}\\p{M}\\p{S}]+@gmail\\.com$")
        if (emailRegex.matches(emailValue.value)) {
            isEmailValid.value = false
            emailErrMsg.value = ""
        } else {
            isEmailValid.value = true
            emailErrMsg.value = "Email doesnt have the correct format:example@gmail.com"
        }
        shouldEnabledConfirmButton()
    }

    fun validatePassword() {
        val passwordRegex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$")

        if (passwordRegex.matches(passwordValue.value)) {
            isPasswordValid.value = false
            passwordErrMsg.value = ""
        } else {
            isPasswordValid.value = true
            passwordErrMsg.value =
                "Password needs have at least 1 Mayus, 1 Number and be more >= 6 chars"
        }
        shouldEnabledConfirmButton()
    }

    fun confirmPassword() {
        if (passwordValue.value == confirmPasswordValue.value) {
            isConfirmPasswordValid.value = false
            confirmPasswordErrMsg.value = ""
        } else {
            isConfirmPasswordValid.value = true
            confirmPasswordErrMsg.value =
                "Passwords do not match"
        }
        shouldEnabledConfirmButton()
    }

    //Reset the fields values
    fun resetValues(){
        usernameValue.value = ""
        emailValue.value = ""
        passwordValue.value = ""
        confirmPasswordValue.value = ""
        _signUpFlow.value = null
    }

    //Call the repository and create an new user
    fun signUp(username: String, email: String, password: String) = viewModelScope.launch {
        _signUpFlow.value = Resource.Loading()
        addUserUseCase(username,email,password)
    }
}