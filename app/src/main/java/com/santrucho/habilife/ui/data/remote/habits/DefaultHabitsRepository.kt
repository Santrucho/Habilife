package com.santrucho.habilife.ui.data.remote.habits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(private val firestore: FirebaseFirestore
        ,private val firebaseAuth: FirebaseAuth) : HabitsRepository {

    override suspend fun addHabit(
        title: String,
        description: String,
        type:String,
        frequently : List<String>,
        timePicker : String,
        isCompleted: Boolean,
        isExpanded: Boolean
    ): Resource<Habit> {

        return try {
            firebaseAuth.currentUser.let { userLogged ->
                var documentReference = firestore.collection("habits").document()

                val habitToSave = Habit(
                    id = documentReference.id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    type = type,
                    frequently = frequently,
                    timePicker = timePicker,
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