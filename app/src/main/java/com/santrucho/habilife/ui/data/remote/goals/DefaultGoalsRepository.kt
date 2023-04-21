package com.santrucho.habilife.ui.data.remote.goals

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultGoalsRepository @Inject constructor(private val firestore:FirebaseFirestore,
                                                 private val firebaseAuth:FirebaseAuth,
) : GoalsRepository {

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
            val resultData = firestore.collection("goalsOption")
                .get().await().toObjects(GoalsOption::class.java)
            Resource.Success(resultData)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun completeGoal(goalId:String,goalCount:Int,goalComplete:Boolean) {
        try {
            firestore.collection("goals").document(goalId).update("completed",goalComplete).await()
            val userId = firebaseAuth.currentUser
            val userCollection = firestore.collection("users")
            val query = userCollection.whereEqualTo("userId", userId?.uid).get().await()
            if (!query.isEmpty) {
                val userDocument = query.documents[0]
                userCollection.document(userDocument.id)
                    .update("goalComplete", goalCount)
                    .await()
            }
        }
        catch(e:Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun getGoalsCompleted(): Int? {
        val userId = firebaseAuth.currentUser?.uid
        val userCollection = firestore.collection("users")
        val query = userCollection.whereEqualTo("userId", userId).get().await()
        return if (!query.isEmpty) {
            val userDocument = query.documents[0]
            userDocument.getLong("goalComplete")?.toInt()
        } else {
            null
        }
    }

}
