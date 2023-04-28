package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultSignUpRepository @Inject constructor(private val firebaseAuth : FirebaseAuth,private val firebaseFirestore:FirebaseFirestore) :
    SignUpRepository {

    override suspend fun createUser(
        username: String,
        email: String,
        password: String,
    ) : Resource<User> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())

            val currentUser = firebaseAuth.currentUser
            val firestoreCollection = firebaseFirestore.collection("users").document()
            val firestoreMessaging : FirebaseMessaging = FirebaseMessaging.getInstance()
            //Added user to firestore database
            currentUser.let{ userLogged ->
                val userToSave = User(
                    userId = userLogged?.uid!!,
                    username = username,
                    email = email,
                    token = firestoreMessaging.token.await()
                )
                firestoreCollection.set(userToSave).await()
                Resource.Success(userToSave)
            }
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }
}