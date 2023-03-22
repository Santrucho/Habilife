package com.santrucho.habilife.ui.data.remote.habits


import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.util.Resource


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

    suspend fun updateHabit(habitId:String,isChecked:Boolean,habitCount:Int)

    suspend fun getOptions() : Resource<List<String>>

    suspend fun getDaysOfWeek() : Resource<List<String>>

    suspend fun getHabitComplete() : Int?

}