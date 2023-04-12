package com.santrucho.habilife.ui.data.model

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.time.LocalTime

data class Habit(
    var id: String="",
    var userId: String="",
    var title:String="",
    var type:String="",
    var frequently: List<String> = emptyList(),
    var timePicker: String = "",
    var completed: Boolean=false,
    var daysCompleted: MutableList<String> = mutableListOf(),
    var finish : Boolean = false
) {
}