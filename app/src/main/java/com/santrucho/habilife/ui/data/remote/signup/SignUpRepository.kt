package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.util.Resource

interface SignUpRepository {

    val currentUser : FirebaseUser?

    suspend fun createUser(username:String,email:String,password:String) : Resource<FirebaseUser>

    fun logout()

}