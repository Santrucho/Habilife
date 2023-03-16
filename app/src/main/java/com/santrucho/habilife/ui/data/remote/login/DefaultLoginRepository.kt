package com.santrucho.habilife.ui.data.remote.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(private val firebaseAuth : FirebaseAuth) :
    LoginRepository {

    override val currentUser : FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(result.user!!)
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }

    override fun logout(){
        firebaseAuth.signOut()
    }
}