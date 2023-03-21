package com.santrucho.habilife.ui.data.remote.habits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(private val firestore: FirebaseFirestore
        ,private val firebaseAuth: FirebaseAuth) : HabitsRepository {

    override suspend fun addHabit(
        title: String,
        description: String,
        type:String,
        frequently : List<String>,
        timePicker : String,
        completed: Boolean
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
                    completed = completed,
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

    override suspend fun updateHabit(habitId:String,isChecked:Boolean){
        firestore.collection("habits").document(habitId).update("completed",isChecked).await()
    }

    override suspend fun getOptions(): Resource<List<String>> {
        return try {
            val result = firestore.collection("typeOptions").get().await()
                .documents.flatMap { it.data?.values?.mapNotNull { value -> value as String? } ?: emptyList() }
            Resource.Success(result)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun getDaysOfWeek(): Resource<List<String>>{
        return try {
            val result = firestore.collection("days").get().await()
                .documents.flatMap { it.data?.values?.mapNotNull { value -> value as String? } ?: emptyList() }
            Resource.Success(result)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}