package com.santrucho.habilife.ui.data.remote.habits

import android.content.ContentValues.TAG
import android.util.Log
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

    override suspend fun updateHabit(habitId:String,isChecked:Boolean,habitCount:Int,daysCompleted:MutableList<String>){
        try {
            firestore.collection("habits").document(habitId).update("completed", isChecked).await()
            firestore.collection("habits").document(habitId).update("daysCompleted",daysCompleted).await()

            val userId = firebaseAuth.currentUser
            val userCollection = firestore.collection("users")
            val query = userCollection.whereEqualTo("userId", userId?.uid).get().await()
            if (!query.isEmpty) {
                val userDocument = query.documents[0]
                userCollection.document(userDocument.id)
                    .update("habitComplete", habitCount)
                    .await()
            }
        }catch(e:Exception){
            Log.e(TAG, "Error updating habit: $e")
        }
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

    override suspend fun getHabitComplete(): Int? {
        val userId = firebaseAuth.currentUser?.uid
        val userCollection = firestore.collection("users")
        val query = userCollection.whereEqualTo("userId", userId).get().await()
        return if (!query.isEmpty) {
            val userDocument = query.documents[0]
            userDocument.getLong("habitComplete")?.toInt()
        } else {
            null
        }
    }

    override suspend fun getHabitsDateCompleted(): MutableList<String> {

        val userId = firebaseAuth.currentUser?.uid
        val userCollection = firestore.collection("habits")
        val query = userCollection.whereEqualTo("userId", userId).get().await()
        return if (!query.isEmpty) {
            val userDocument = query.documents[0]
            val daysCompleted = userDocument.get("daysCompleted") as? List<String>
            daysCompleted?.toMutableList() ?: mutableListOf()
        } else {
            mutableListOf<String>()
        }
    }
}