package com.santrucho.habilife.ui.domain.signup

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.signup.SignUpRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val repository: SignUpRepository){

    suspend operator fun invoke(
        username:String,
        email:String,
        password:String): Resource<User> {
        return try{
            repository.createUser(username,email,password)
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}