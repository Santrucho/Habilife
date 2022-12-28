package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.utils.Resource

interface SignUpRepository {

    val currentUser : FirebaseUser?

    suspend fun createUser(username:String,email:String,password:String) : Resource<FirebaseUser>

    fun logout()

}