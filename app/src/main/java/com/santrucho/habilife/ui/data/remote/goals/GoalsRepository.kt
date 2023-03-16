package com.santrucho.habilife.ui.data.remote.goals


import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.util.Resource

interface GoalsRepository {

    suspend fun addGoal(
        title:String,
        description:String,
        isCompleted : Boolean,
        release_date:String) : Resource<GoalsResponse>

    suspend fun getGoals() : Resource<List<GoalsResponse>>

    suspend fun deleteGoal(goal: GoalsResponse)

    suspend fun getGoalsOptions() : Resource<List<GoalsOption>>
}