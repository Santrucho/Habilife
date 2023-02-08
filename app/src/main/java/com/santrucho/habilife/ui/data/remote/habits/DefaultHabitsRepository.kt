package com.santrucho.habilife.ui.data.remote.habits

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(private val firestore: FirebaseFirestore
        ,private val firebaseAuth: FirebaseAuth) : HabitsRepository {

    var documentReference = firestore.collection("habits").document()

    override suspend fun addHabit(
        title: String,
        description: String,
        image: String,
        frequently: String,
        isCompleted: Boolean,
        isExpanded: Boolean
    ): Resource<Habit> {

        return try {
            firebaseAuth.currentUser.let { userLogged ->
                val habitToSave = Habit(
                    id = documentReference.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    image = image,
                    frequently = frequently,
                    isCompleted = isCompleted,
                    isExpanded = isExpanded
                )
                documentReference.set(habitToSave).await()
                Resource.Success(habitToSave)
            }
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }

    override suspend fun getHabits(): Resource<List<Habit>> {
        return try {
            val resultData = firestore.collection("habits")
                .whereEqualTo("userId",firebaseAuth.currentUser?.uid)
                .get().await().toObjects(Habit::class.java)
            Resource.Success(resultData)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        try {
            firestore.collection("habits").document(habit.id).delete().await()
        }
        catch(e:Exception){
            Resource.Failure(e)
        }
    }
}