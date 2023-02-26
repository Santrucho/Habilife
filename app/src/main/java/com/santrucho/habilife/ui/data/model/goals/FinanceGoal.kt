package com.santrucho.habilife.ui.data.model.goals

data class FinanceGoal(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "",
    //New parameters
    var amount : Int = 0,
    var addAmount : Int = 0,
    var amountGoal : String = ""
) {
}