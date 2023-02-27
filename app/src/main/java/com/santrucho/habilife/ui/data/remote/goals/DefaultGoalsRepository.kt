package com.santrucho.habilife.ui.data.remote.goals

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultGoalsRepository @Inject constructor(private val firestore:FirebaseFirestore,
                                                 private val firebaseAuth:FirebaseAuth,
) : GoalsRepository {

    override suspend fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String
    ): Resource<GoalsResponse> {
        return try {

            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = GoalsResponse(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        }
        catch(e:Exception) {
            return Resource.Failure(e)
        }
}
    override suspend fun getGoals(): Resource<List<GoalsResponse>> {
        return try {
            val resultData = firestore.collection("goals")
                .whereEqualTo("userId",firebaseAuth.currentUser?.uid)
                .get().await().toObjects(GoalsResponse::class.java)
            Resource.Success(resultData)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteGoal(goal: GoalsResponse){
        try {
            firestore.collection("goals").document(goal.id).delete().await()
        }
        catch(e:Exception){
            Resource.Failure(e)
        }
    }

    //Call the options to select goals
    override suspend fun getGoalsOptions(): Resource<List<GoalsOption>> {
        return try {
            val resultData = firestore.collection("goalsOptions")
                .get().await().toObjects(GoalsOption::class.java)
            Resource.Success(resultData)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

}
