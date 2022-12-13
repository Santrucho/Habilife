package com.santrucho.habilife.ui.data.repository

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.utils.Resource

interface SignUpRepository {

    val currentUser : FirebaseUser?

    suspend fun createUser(username:String,email:String,password:String) : Resource<FirebaseUser>

}