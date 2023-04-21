package com.santrucho.habilife.ui.domain.login

import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository : LoginRepository) {

    suspend operator fun invoke(email:String,password:String) : Resource<User> {
        return try{
            repository.loginUser(email,password)
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}