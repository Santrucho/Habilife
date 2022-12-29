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
            firebaseAuth.currentUser.let { userLogged ->
                val goalToSave = Goals(
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date
                )
                firestore.collection("goals").add(goalToSave).await()

                Resource.Success(goalToSave)
            }
        }
        catch(e:Exception) {
            return Resource.Failure(e)
        }
}
    override suspend fun getGoals(): Resource<List<Goals>> {
        return try {
            val goalsList = mutableListOf<Goals>()
            val resultData = firestore.collection("goals")
                .whereEqualTo("userId",firebaseAuth.currentUser?.uid)
                .get().await()

            for (document in resultData){
                val title = document.getString("title")
                val description = document.getString("description")
                val release_date = document.getString("release_date")
                goalsList.add(Goals(firebaseAuth.currentUser?.uid!!,title!!,description!!,false,release_date!!))
            }

            Resource.Success(goalsList)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

}
