package com.santrucho.habilife.ui.data.remote.habits


import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.util.Resource


interface HabitsRepository {

    suspend fun addHabit(
        title:String,
        type:String,
        frequently : List<String>,
        timePicker : String,
        completed : Boolean) : Resource<Habit>

    suspend fun getHabits() : Resource<List<Habit>>

    suspend fun deleteHabit(habit:Habit)

    suspend fun updateHabit(habitId:String,isChecked:Boolean,daysCompleted:MutableList<String>)

    suspend fun getOptions() : Resource<List<String>>

    suspend fun getDaysOfWeek() : Resource<List<String>>

    suspend fun getHabitComplete() : Int?

    suspend fun getHabitsDateCompleted() : MutableList<String>

    suspend fun finishHabit(habitId:String,habitCount:Int,habitFinish:Boolean)

}