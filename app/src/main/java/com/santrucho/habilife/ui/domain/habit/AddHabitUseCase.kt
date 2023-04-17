package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(private val repository: HabitsRepository){

    suspend operator fun invoke(
        title:String,
        type:String,
        frequently:List<String>,
        timePicker:String,
        completed:Boolean
    ) : Resource<Habit> {
        return try {
            repository.addHabit(title, type, frequently, timePicker, completed)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}