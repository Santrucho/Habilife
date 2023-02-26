package com.santrucho.habilife.ui.data.model

data class Goals(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var duration : String = "",
    var image : String = "",
    var type : String = ""
) {
}