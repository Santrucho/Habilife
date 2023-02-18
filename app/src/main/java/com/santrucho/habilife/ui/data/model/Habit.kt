package com.santrucho.habilife.ui.data.model

import java.time.LocalTime

data class Habit(
    var id: String="",
    var userId: String="",
    var title:String="",
    var description:String="",
    var type:String="",
    var frequently: List<String> = emptyList(),
    var timePicker: String = "",
    var isCompleted: Boolean=false,
    var isExpanded: Boolean=false
) {
}