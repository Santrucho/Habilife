package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repository:HabitsRepository) {

    suspend operator fun invoke(habit: Habit) : Resource<Habit> {
        return try {
            repository.deleteHabit(habit)
            Resource.Success(habit)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}