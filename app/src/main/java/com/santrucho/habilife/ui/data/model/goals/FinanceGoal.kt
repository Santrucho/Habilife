package com.santrucho.habilife.ui.data.model.goals

data class FinanceGoal(
    var id: String = "",
    var userId: String = "",
    var title: String = "",
    var description: String = "",
    var completed: Boolean = false,
    var release_date: String = "",
    var image: String = "",
    var type: String = "Finance",
    var amount: Int? = 0,
    var amountGoal: Int? = 0,
) {
}
