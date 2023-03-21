package com.santrucho.habilife.ui.data.remote.goals.learning

import com.santrucho.habilife.ui.data.model.goals.LearningGoal
import com.santrucho.habilife.ui.util.Resource

interface LearningRepository {

    suspend fun addLearningGoal(title: String,
                               description: String,
                               isCompleted: Boolean,
                               release_date: String,
                               timesAWeek : Int,
    ) : Resource<LearningGoal>

}