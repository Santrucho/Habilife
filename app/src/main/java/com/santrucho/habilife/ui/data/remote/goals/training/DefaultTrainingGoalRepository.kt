package com.santrucho.habilife.ui.data.remote.goals.training

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultTrainingGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                        private val firebaseAuth: FirebaseAuth,
                                                        private val fireStorage: FirebaseStorage
) : TrainingGoalRepository {

    override suspend fun addRunningGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        kilometers: Int?,
        kilometersGoal: Int?
    ): Resource<TrainingGoal> {
        return try {
            val storageRef = fireStorage.reference.child("gymone.png")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = TrainingGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    kilometers = kilometers,
                    kilometersGoal = kilometersGoal
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    override suspend fun updateGoal(goalId:String,kilometers: Int?){
        firestore.collection("goals").document(goalId).update("kilometers",kilometers).await()
    }
}