package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(private val repository: HabitsRepository){

    suspend operator fun invoke(habitId:String,
                                isChecked:Boolean,
                                daysCompleted:MutableList<String>){
        try{
            repository.updateHabit(habitId,
                isChecked,
                daysCompleted)
        } catch (e:Exception){
            Log.d("Error:",e.message.toString())
        }
    }
}