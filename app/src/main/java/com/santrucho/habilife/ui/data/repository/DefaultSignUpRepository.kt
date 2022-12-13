package com.santrucho.habilife.ui.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultSignUpRepository @Inject constructor(private val firebaseAuth : FirebaseAuth) : SignUpRepository {

    override val currentUser : FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun createUser(
        username: String,
        email: String,
        password: String,
    ) : Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())
            Resource.Success(result.user!!)
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }


}