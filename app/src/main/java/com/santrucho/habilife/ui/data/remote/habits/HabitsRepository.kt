package com.santrucho.habilife.ui.data.remote.habits


import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource


interface HabitsRepository {

    suspend fun addHabit(
        title:String,
        description:String,
        type:String,
        frequently : List<String>,
        timePicker : String,
        completed : Boolean) : Resource<Habit>

    suspend fun getHabits() : Resource<List<Habit>>

    suspend fun deleteHabit(habit:Habit)

    suspend fun updateHabit(habitId:String,isChecked:Boolean)

    suspend fun getOptions() : Resource<List<String>>

    suspend fun getDaysOfWeek() : Resource<List<String>>

}