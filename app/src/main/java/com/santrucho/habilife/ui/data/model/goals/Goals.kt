package com.santrucho.habilife.ui.data.model.goals

data class GoalsResponse(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "",
    var amount : Int? = null,
    val subject : String = "",
    val actualJob : String = "",
    val kilometers: Int? = null
    ) {
}