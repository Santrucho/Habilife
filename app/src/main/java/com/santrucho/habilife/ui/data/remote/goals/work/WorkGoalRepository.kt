package com.santrucho.habilife.ui.data.remote.goals.work

import com.santrucho.habilife.ui.data.model.goals.WorkGoal
import com.santrucho.habilife.ui.util.Resource

interface WorkGoalRepository {

    suspend fun addWorkGoal(title: String,
                       description: String,
                       isCompleted: Boolean,
                       release_date: String,
                       actualJob : String?,
                       jobGoal : String) : Resource<WorkGoal>

}