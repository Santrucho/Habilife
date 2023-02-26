package com.santrucho.habilife.ui.data.model.goals

data class WorkGoal(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "",
    //New parameters
    var actualJob : String = "",
    var jobGoal : String = ""
) {
}