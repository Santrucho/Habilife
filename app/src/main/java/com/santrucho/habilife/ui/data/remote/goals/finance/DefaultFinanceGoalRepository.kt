package com.santrucho.habilife.ui.data.remote.goals.finance

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class DefaultFinanceGoalRepository @Inject constructor(private val firestore: FirebaseFirestore,
                                                       private val firebaseAuth: FirebaseAuth,
                                                       private val fireStorage:FirebaseStorage
) : FinanceGoalRepository {

    override suspend fun addFinanceGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        amount : Int?,
        amountGoal : Int?
    ): Resource<FinanceGoal> {
        return try {
            val storageRef = fireStorage.reference.child("moneygreen.png")
            val downloadUrl = storageRef.downloadUrl.await()
            firebaseAuth.currentUser.let { userLogged ->
                val docRef = firestore.collection("goals").document()
                val goalToSave = FinanceGoal(
                    id = docRef.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    release_date = release_date,
                    image = downloadUrl.toString(),
                    amount = amount,
                    amountGoal = amountGoal
                )
                docRef.set(goalToSave).await()
                Resource.Success(goalToSave)
            }
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    override suspend fun updateGoal(goalId:String,amount: Int?){
        firestore.collection("goals").document(goalId).update("amount",amount).await()
    }
}