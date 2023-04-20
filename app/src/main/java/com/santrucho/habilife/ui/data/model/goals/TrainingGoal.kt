package com.santrucho.habilife.ui.data.model.goals

data class TrainingGoal(
    var id: String = "",
    var userId: String = "",
    var title: String = "",
    var description: String = "",
    var completed: Boolean = false,
    var release_date: String = "",
    var image: String = "",
    var type: String = "Training",
    var kilometers: Int? = 0,
    var kilometersGoal: Int? = 0
) {
}
