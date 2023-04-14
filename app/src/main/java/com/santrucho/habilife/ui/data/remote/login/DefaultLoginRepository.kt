package com.santrucho.habilife.ui.data.remote.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(private val firebaseAuth : FirebaseAuth,private val firestore : FirebaseFirestore) :
    LoginRepository {

    val firebaseMessaging : FirebaseMessaging = FirebaseMessaging.getInstance()

    override val currentUser : FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try{
            val token = firebaseMessaging.token.await()
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            updateTokenFCM(token)
            Resource.Success(result.user!!)
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }

    override fun logout(){
        firebaseAuth.signOut()
    }

    override suspend fun updateTokenFCM(newToken:String) {

        val userId = firebaseAuth.currentUser?.uid
        val docRef = firestore.collection("users").whereEqualTo("userId",userId)

        if (userId != null) {
            val userDoc = docRef.get().await()
            if(!userDoc.isEmpty){
                val userRef = firestore.collection("users").document(userDoc.documents[0].id)
                userRef.update("token", newToken).await()
            }
        }
    }
}