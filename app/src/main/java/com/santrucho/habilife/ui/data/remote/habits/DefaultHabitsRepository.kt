package com.santrucho.habilife.ui.data.remote.habits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(private val firestore: FirebaseFirestore
        ,private val firebaseAuth: FirebaseAuth) : HabitsRepository {


    override suspend fun addHabit(id:String,
                                  title:String,
                                  description:String,
                                  image:String,
                                  frequently:String,
                                  isCompleted : Boolean,
                                  isExpanded : Boolean): Resource<Habit> {

        return try {
            firebaseAuth.currentUser.let { userLogged ->
                val habitToSave = Habit(
                    id = id,
                    userId = userLogged?.uid.toString(),
                    title = title,
                    description = description,
                    image = image,
                    frequently = frequently,
                    isCompleted = isCompleted,
                    isExpanded = isExpanded
                )
                firestore.collection("habits").add(habitToSave).await()

                Resource.Success(habitToSave)
            }
        } catch(e:Exception){
            return Resource.Failure(e)
        }
    }

    override suspend fun getHabits(): Resource<List<Habit>> {
        return try {
            val habitList = mutableListOf<Habit>()
            val resultData = firestore.collection("habits")
                .whereEqualTo("userId",firebaseAuth.currentUser?.uid)
                .get().await()

            for (document in resultData){
                val title = document.getString("title")
                val description = document.getString("description")
                val frequently = document.getString("frequently")
                habitList.add(Habit(id = "",firebaseAuth.currentUser?.uid!!,title!!,description!!,"",frequently!!,false,false))
            }

            Resource.Success(habitList)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }

}