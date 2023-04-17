package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import javax.inject.Inject

class GetHabitCompleteUseCase @Inject constructor(private val repository: HabitsRepository) {

    suspend operator fun invoke():Int?{
        return try{
            repository.getHabitComplete()
        } catch(e:Exception){
            Log.d("Error",e.message.toString())
        }
    }
}