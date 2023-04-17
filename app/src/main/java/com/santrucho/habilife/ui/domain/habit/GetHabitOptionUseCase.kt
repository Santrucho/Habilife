package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class GetHabitOptionUseCase @Inject constructor(private val repository: HabitsRepository) {

    suspend operator fun invoke() : Resource<List<String>> {
        return try{
            repository.getOptions()
        } catch(e:Exception){
            Resource.Failure(e)
        }
    }
}