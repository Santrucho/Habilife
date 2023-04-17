package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class FinishHabitUseCase @Inject constructor(private val repository: HabitsRepository) {

    suspend operator fun invoke(habitId:String,habitCount:Int,habitFinish:Boolean) : Resource<String> {
        return try {
            repository.finishHabit(habitId,habitCount,habitFinish)
            Resource.Success("Habit finished successfully")
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}