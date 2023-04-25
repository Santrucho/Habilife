package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.util.Resource

interface SignUpRepository {

    suspend fun createUser(username:String,email:String,password:String) : Resource<User>

}