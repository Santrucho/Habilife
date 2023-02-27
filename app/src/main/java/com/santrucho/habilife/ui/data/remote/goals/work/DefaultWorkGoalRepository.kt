package com.santrucho.habilife.ui.data.remote.goals.work

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.WorkGoal
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultWorkGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                    private val firebaseAuth: FirebaseAuth,
                                                    private val fireStorage: FirebaseStorage
) : WorkGoalRepository {

    override suspend fun addWorkGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        actualJob : String,
        jobGoal : String
    ): Resource<WorkGoal> {
        return try {
            val storageRef = fireStorage.reference.child("office.jpg")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = WorkGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    actualJob = actualJob,
                    jobGoal = jobGoal
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}