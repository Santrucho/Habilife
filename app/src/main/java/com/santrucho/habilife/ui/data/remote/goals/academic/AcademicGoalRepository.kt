package com.santrucho.habilife.ui.data.remote.goals.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.util.Resource

interface AcademicGoalRepository {

    suspend fun addAcademicGoal(title: String,
                                description: String,
                                isCompleted: Boolean,
                                release_date: String,
                                subject : String?,
                                subjectList : List<String>?,
                                subjectApproved:List<String>?

    ) : Resource<AcademicGoal>

    suspend fun updateGoal(goalId:String,subjectApproved: List<String>?)
}