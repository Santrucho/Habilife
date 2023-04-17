package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repository:HabitsRepository) {

    suspend operator fun invoke(habit: Habit){
        try {
            repository.deleteHabit(habit)
        } catch (e:Exception){
            Log.d("Error: ",e.message.toString())
        }
    }
}