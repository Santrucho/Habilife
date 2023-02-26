package com.santrucho.habilife.ui.data.remote.goals.finance

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.utils.Resource

interface FinanceGoalRepository {

    fun addFinanceGoal(title: String,
                       description: String,
                       isCompleted: Boolean,
                       release_date: String) : Resource<FinanceGoal>
}