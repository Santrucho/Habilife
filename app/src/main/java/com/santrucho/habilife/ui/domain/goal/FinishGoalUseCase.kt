package com.santrucho.habilife.ui.domain.goal

import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class FinishGoalUseCase @Inject constructor(private val goalRepository: GoalsRepository) {

    suspend operator fun invoke(goalId:String) : Resource<String>{
        return try {
            goalRepository.finishGoal(goalId)
            Resource.Success("Goal finished successfully")
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}