package com.santrucho.habilife.ui.data.remote.login

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.util.Resource

interface LoginRepository {

    suspend fun currentUser() : Resource<User>

    suspend fun loginUser(email:String,password:String) : Resource<User>

    fun logout() : Resource<Unit>

    suspend fun updateTokenFCM(newToken:String)
}