package com.santrucho.habilife.ui.domain.goal

import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class ExtendedGoalUseCase @Inject constructor(private val repository: GoalsRepository) {

    suspend operator fun invoke(goalId:String,newDate:String) : Resource<String> {
        return try{
            repository.extendedGoal(goalId,newDate)
            Resource.Success("Goal extended successfully")
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}