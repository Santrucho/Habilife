package com.santrucho.habilife.ui.data.model

data class GoalsResponse(
    val isLoading : Boolean = false,
    val goalsList : List<Goals> = emptyList(),
    val error : String = ""
) {
}