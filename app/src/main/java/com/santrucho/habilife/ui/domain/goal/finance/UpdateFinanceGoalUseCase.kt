package com.santrucho.habilife.ui.domain.goal.finance

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class UpdateFinanceGoalUseCase @Inject constructor(private val financeRepository : FinanceGoalRepository) {

    suspend operator fun invoke(goalId:String,amount:Int?) : Resource<Unit> {
        return try{
            financeRepository.updateGoal(goalId, amount)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}