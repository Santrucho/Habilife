package com.santrucho.habilife.ui.data.remote.habits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(private val firestore: FirebaseFirestore
        ,private val firebaseAuth: FirebaseAuth) : HabitsRepository {

    override suspend fun addHabit(
        habit:Habit
    ): Resource<Habit> {

        return try {
            firebaseAuth.currentUser.let { userLogged ->
                var documentReference = firestore.collection("habits").document()
                val habitMap = hashMapOf(
                    "id" to documentReference.id,
                    "userId" to userLogged?.uid.toString(),
                    "title" to habit.title,
                    "description" to  habit.description,
                    "type" to  habit.type.name,
                    "frequently" to  habit.frequently,
                    "isCompleted" to  habit.isCompleted,
                    "isExpanded" to habit.isExpanded
                )
                documentReference.set(habitMap).await()
                Resource.Success(habit)
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