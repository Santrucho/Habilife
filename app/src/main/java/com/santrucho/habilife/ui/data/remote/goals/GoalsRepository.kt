package com.santrucho.habilife.ui.data.remote.goals


import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.data.model.GoalsOption
import com.santrucho.habilife.ui.utils.Resource

interface GoalsRepository {

    suspend fun addGoal(
        title:String,
        description:String,
        isCompleted : Boolean,
        release_date:String) : Resource<Goals>

    suspend fun getGoals() : Resource<List<Goals>>

    suspend fun deleteGoal(goal:Goals)

    suspend fun getGoalsOptions() : Resource<List<GoalsOption>>
}