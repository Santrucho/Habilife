package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultSignUpRepository @Inject constructor(private val firebaseAuth : FirebaseAuth,private val firebaseFirestore:FirebaseFirestore) :
    SignUpRepository {

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

            //Added user to firestore database
            currentUser.let{ userLogged ->
                val userToSave = User(
                    userId = userLogged?.uid!!,
                    username = username,
                    email = email
                )
                firebaseFirestore.collection("users").add(userToSave).await()
            }

            Resource.Success(result.user!!)
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}