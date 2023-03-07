package com.santrucho.habilife.ui.data.remote.goals.academic

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultAcademicGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                        private val firebaseAuth: FirebaseAuth,
                                                        private val fireStorage:FirebaseStorage) : AcademicGoalRepository {

    override suspend fun addAcademicGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        subject: String?,
        subjectApprove: Int,
        subjectGoal: Int
    ): Resource<AcademicGoal> {
        return try {
            val storageRef = fireStorage.reference.child("Mi proyecto.jpg")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->

                val docRef = firestore.collection("goals").document()
                val goalToSave = AcademicGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    subject = subject,
                    subjectApprove = subjectApprove,
                    subjectGoal = subjectGoal
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)

            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}