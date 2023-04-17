package com.santrucho.habilife.ui.domain.habit

import android.util.Log
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class GetCompletedDatesUseCase @Inject constructor(private val repository: HabitsRepository){

    suspend operator fun invoke() : MutableList<String>{
        return try{
            repository.getHabitsDateCompleted()
        } catch (e:Exception){
            mutableListOf()
        }
    }
}