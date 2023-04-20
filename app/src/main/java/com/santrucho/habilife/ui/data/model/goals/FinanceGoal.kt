package com.santrucho.habilife.ui.data.model.goals

data class FinanceGoal(
    override var id: String = "",
    override var userId: String = "",
    override var title: String = "",
    override var description: String = "",
    override var completed: Boolean = false,
    override var release_date: String = "",
    override var image: String = "",
    override var type : String = "Finance",
    override var amount : Int? = 0,
    override var amountGoal : Int? = 0,
) : GoalsResponse(){
}
