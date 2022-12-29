package com.santrucho.habilife.ui.data.model

data class Habit(
    var id : String,
    var userId : String,
    var title:String,
    var description:String,
    var image:String,
    var frequently:String,
    var isCompleted : Boolean,
    var isExpanded : Boolean
) {
}