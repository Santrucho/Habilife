package com.santrucho.habilife.ui.data.model.goals

data class LearningGoal(
    var id: String = "",
    var userId: String = "",
    var title: String = "",
    var description: String = "",
    var completed: Boolean = false,
    var release_date: String = "",
    var image: String = "",
    var type: String = "Learning",
    var timesAWeek: Int? = 0,
) {
}