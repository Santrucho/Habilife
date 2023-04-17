package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import javax.inject.Inject

class FinishHabitUseCase @Inject constructor(private val repository: HabitsRepository) {

    suspend operator fun invoke(habitId:String,habitCount:Int,habitFinish:Boolean) {
        try {
            repository.finishHabit(habitId,habitCount,habitFinish)
        } catch (e:Exception){
            Log.d("Error",e.message.toString())
        }
    }
}