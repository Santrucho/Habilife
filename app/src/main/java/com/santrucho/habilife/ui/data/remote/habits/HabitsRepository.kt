package com.santrucho.habilife.ui.data.remote.habits


import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.utils.Resource


interface HabitsRepository {

    suspend fun addHabit(
        title:String,
        description:String,
        image:String,
        frequently:String,
        isCompleted : Boolean,
        isExpanded : Boolean) : Resource<Habit>

    suspend fun getHabits() : Resource<List<Habit>>

    suspend fun deleteHabit(habit:Habit)

}