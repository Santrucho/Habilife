package com.santrucho.habilife.ui.data.remote.goals


import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.utils.Resource

interface GoalsRepository {

    suspend fun addGoal(
        userId:String,
        title:String,
        description:String,
        isCompleted : Boolean,
        release_date:String) : Resource<Goals>

    suspend fun getGoals() : Resource<List<Goals>>
}