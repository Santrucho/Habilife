package com.santrucho.habilife.ui.data.remote.goals.training

import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.util.Resource

interface TrainingGoalRepository {

    suspend fun addGoal(title: String,
                        description: String,
                        isCompleted: Boolean,
                        release_date: String,
                        kilometers:Int?,
                        kilometersGoal : Int?
    ) : Resource<TrainingGoal>

    suspend fun updateGoal(goalId:String,kilometers: Int?) : Resource<Unit>
}