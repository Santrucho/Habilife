package com.santrucho.habilife.ui.data.remote.goals


import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.util.Resource

interface GoalsRepository {

    suspend fun getGoals() : Resource<List<GoalsResponse>>

    suspend fun deleteGoal(goal: GoalsResponse)

    suspend fun getGoalsOptions() : Resource<List<GoalsOption>>

    suspend fun getGoalsCompleted() : Int?

    suspend fun finishGoal(goalId: String)

    suspend fun extendedGoal(goalId:String,newDate:String)
}