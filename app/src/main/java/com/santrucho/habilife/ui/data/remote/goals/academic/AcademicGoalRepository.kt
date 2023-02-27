package com.santrucho.habilife.ui.data.remote.goals.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.utils.Resource

interface AcademicGoalRepository {

    suspend fun addAcademicGoal(title: String,
                                description: String,
                                isCompleted: Boolean,
                                release_date: String,
                                subject : String,
                                subjectApprove : Int,
                                subjectGoal : Int
    ) : Resource<AcademicGoal>
}