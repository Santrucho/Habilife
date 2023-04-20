package com.santrucho.habilife.ui.domain.goal.learning

import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.model.goals.LearningGoal
import com.santrucho.habilife.ui.data.remote.goals.learning.LearningRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddLearningGoalUseCase @Inject constructor(private val learningRepository: LearningRepository) {

    suspend operator fun invoke(title: String,
                                description: String,
                                isCompleted: Boolean,
                                release_date: String,
                                timesAWeek: Int) : Resource<LearningGoal> {
        return try{
            learningRepository.addGoal(title,description, isCompleted, release_date, timesAWeek)
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}