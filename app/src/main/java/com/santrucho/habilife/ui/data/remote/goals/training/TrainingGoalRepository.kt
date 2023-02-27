package com.santrucho.habilife.ui.data.remote.goals.training

import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.utils.Resource

interface TrainingGoalRepository {

    suspend fun addTrainingGoal(title: String,
                       description: String,
                       isCompleted: Boolean,
                       release_date: String,
                       kilometers:Int?,
                       kilometersGoal : Int?
    ) : Resource<TrainingGoal>
}