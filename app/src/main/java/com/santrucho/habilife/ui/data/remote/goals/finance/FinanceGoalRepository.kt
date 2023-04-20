package com.santrucho.habilife.ui.data.remote.goals.finance

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.util.Resource

interface FinanceGoalRepository {

    suspend fun addGoal(title: String,
                        description: String,
                        isCompleted: Boolean,
                        release_date: String,
                        amount : Int?,
                        amountGoal : Int?
    ) : Resource<FinanceGoal>

    suspend fun updateGoal(goalId:String,amount:Int?) : Resource<Unit>
}