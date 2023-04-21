package com.santrucho.habilife.ui.domain.goal.training

import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class UpdateTrainingGoalUseCase @Inject constructor(private val trainingRepository : TrainingGoalRepository) {

    suspend operator fun invoke(goalId:String,kilometers: Int?) : Resource<Unit> {
        return try {
            trainingRepository.updateGoal(goalId, kilometers)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}