package com.santrucho.habilife.ui.domain.goal.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class UpdateAcademicGoalUseCase @Inject constructor(private val academicRepository: AcademicGoalRepository){

    suspend operator fun invoke(goalId:String,subjectApproved: List<String>) : Resource<Unit> {
        return try{
            academicRepository.updateGoal(goalId,subjectApproved)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }

}