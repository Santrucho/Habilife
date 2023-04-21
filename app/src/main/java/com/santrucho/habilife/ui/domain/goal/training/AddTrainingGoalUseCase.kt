package com.santrucho.habilife.ui.domain.goal.training

import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddTrainingGoalUseCase @Inject constructor(private val trainingRepository: TrainingGoalRepository) {

    suspend operator fun invoke(title: String,
                                description: String,
                                isCompleted: Boolean,
                                release_date: String,
                                kilometers: Int?,
                                kilometersGoal: Int?) : Resource<TrainingGoal> {
        return try{
            trainingRepository.addGoal(title,description, isCompleted, release_date, kilometers, kilometersGoal)
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}