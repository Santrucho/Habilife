package com.santrucho.habilife.ui.data.model.goals

data class TrainingGoal(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "Training",
    //New parameters
    var kilometers : Int? = null,
    var kilometersGoal : Int? = null
) {
}