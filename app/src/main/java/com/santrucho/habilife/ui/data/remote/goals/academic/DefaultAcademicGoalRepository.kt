package com.santrucho.habilife.ui.data.remote.goals.academic

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultAcademicGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                        private val firebaseAuth: FirebaseAuth,
                                                        private val fireStorage:FirebaseStorage) : AcademicGoalRepository {

    override suspend fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        subject: String,
        subjectList:List<String>,
        subjectApproved:List<String>
    ): Resource<AcademicGoal> {
        return try {
            val storageRef = fireStorage.reference.child("books.png")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->

                val docRef = firestore.collection("goals").document()
                val goalToSave = AcademicGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    completed = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    type = "Academic",
                    subject = subject,
                    subjectList = subjectList,
                    subjectApproved = subjectApproved
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)

            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    override suspend fun updateGoal(goalId: String, subjectApproved: List<String>) : Resource<Unit> {
        return try{
            firestore.collection("goals").document(goalId).update("subjectApproved",subjectApproved)
            Resource.Success(Unit)
        } catch(e:Exception){
            Resource.Failure(e)
        }

    }
}