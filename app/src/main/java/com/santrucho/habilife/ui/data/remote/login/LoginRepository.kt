package com.santrucho.habilife.ui.data.remote.login

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.utils.Resource

interface LoginRepository {

    val currentUser : FirebaseUser?
    suspend fun loginUser(email:String,password:String) : Resource<FirebaseUser>
    fun logout()
}