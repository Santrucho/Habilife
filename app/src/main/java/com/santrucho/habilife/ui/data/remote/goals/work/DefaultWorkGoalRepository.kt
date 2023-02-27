package com.santrucho.habilife.ui.data.remote.goals.work

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.WorkGoal
import com.santrucho.habilife.ui.utils.Resource

class DefaultWorkGoalRepository() : WorkGoalRepository {

    override fun addWorkGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String
    ): Resource<WorkGoal> {
        TODO("Not yet implemented")
    }
}