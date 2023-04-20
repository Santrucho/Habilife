package com.santrucho.habilife.ui.domain.goal.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddAcademicGoalUseCase @Inject constructor(private val academicRepository: AcademicGoalRepository) {

    suspend operator fun invoke(title: String,
                                description: String,
                                isCompleted: Boolean,
                                release_date: String,
                                subject: String,
                                subjectList:List<String>,
                                subjectApproved:List<String>): Resource<AcademicGoal> {
        return try{
            academicRepository.addGoal(title,
                description,
                isCompleted,
                release_date,
                subject,
                subjectList,
                subjectApproved)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}