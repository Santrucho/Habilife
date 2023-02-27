package com.santrucho.habilife.ui.data.remote.goals.training

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultTrainingGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                        private val firebaseAuth: FirebaseAuth
) : TrainingGoalRepository {

    override suspend fun addTrainingGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        kilometers: Int?,
        kilometersGoal: Int?
    ): Resource<TrainingGoal> {
        return try {
            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = TrainingGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
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
}