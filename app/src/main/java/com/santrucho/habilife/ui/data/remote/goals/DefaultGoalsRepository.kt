package com.santrucho.habilife.ui.data.remote.goals

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultGoalsRepository @Inject constructor(private val firestore:FirebaseFirestore,
                                                 private val firebaseAuth:FirebaseAuth) : GoalsRepository {

    override suspend fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String
    ): Resource<Goals> {
        return try {
            val docRef = firestore.collection("goals").document()
            firebaseAuth.currentUser.let { userLogged ->
                val goalToSave = Goals(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        }
        catch(e:Exception) {
            return Resource.Failure(e)
        }
}
    override suspend fun getGoals(): Resource<List<Goals>> {
        return try {

            val resultData = firestore.collection("goals")
                .whereEqualTo("userId",firebaseAuth.currentUser?.uid)
                .get().await().toObjects(Goals::class.java)
            Resource.Success(resultData)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

}
