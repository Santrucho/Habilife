package com.santrucho.habilife.ui.data.remote.goals.health

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.utils.Resource

interface TrainingGoalRepository {

    fun addTrainingGoal(title: String,
                       description: String,
                       isCompleted: Boolean,
                       release_date: String) : Resource<TrainingGoal>
}