package com.santrucho.habilife.ui.data.remote.goals.learning

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.LearningGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LearningDefaultRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                    private val firebaseAuth: FirebaseAuth,
                                                    private val fireStorage: FirebaseStorage
):LearningRepository{
    override suspend fun addLearningGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        timesAWeek: Int
    ): Resource<LearningGoal> {
        return try {
            val storageRef = fireStorage.reference.child("learning.png")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = LearningGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    timesAWeek = timesAWeek
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}