package com.santrucho.habilife.ui.data.model.goals

data class FinanceGoal(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "Finance",
    //New parameters
    var amount : Int? = null,
    var amountGoal : String = ""
) {
}