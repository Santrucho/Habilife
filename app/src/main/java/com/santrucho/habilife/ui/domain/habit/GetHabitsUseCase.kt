package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor(private val repository: HabitsRepository) {

    suspend operator fun invoke() : Resource<List<Habit>> {
        return try{
            repository.getHabits()
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}