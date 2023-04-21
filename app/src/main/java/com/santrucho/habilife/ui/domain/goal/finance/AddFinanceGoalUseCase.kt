package com.santrucho.habilife.ui.domain.goal.finance

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddFinanceGoalUseCase @Inject constructor(private val financeRepository: FinanceGoalRepository) {

    suspend operator fun invoke(title: String,
                       description: String,
                       isCompleted: Boolean,
                       release_date: String,
                       amount : Int?,
                       amountGoal : Int?) : Resource<FinanceGoal> {
        return try{
            financeRepository.addGoal(title,description, isCompleted, release_date, amount, amountGoal)
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}