package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.data.model.GoalsResponse
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository : GoalsRepository) : ViewModel() {
    private val _goalFlow = MutableStateFlow<Resource<Goals>?>(null)
    val goalFlow : StateFlow<Resource<Goals>?> = _goalFlow

    private val _goalState = MutableStateFlow<Resource<List<Goals>>?>(null)
    val goalState : StateFlow<Resource<List<Goals>>?> = _goalState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    init {
        getAllGoals()
    }

    fun addGoal(title:String,description:String,isCompleted:Boolean,release_date:String){
        viewModelScope.launch {
            _goalFlow.value = Resource.Loading()
            val createHabit = repository.addGoal(title,description,isCompleted,release_date)
            _goalFlow.value = createHabit
        }

    }

    fun getAllGoals(){
        viewModelScope.launch {
            _goalState.value = repository.getGoals()
        }
    }

    fun deleteGoal(goal:Goals){
        viewModelScope.launch {
            repository.deleteGoal(goal)
            val currentResource = _goalState.value
            val currentGoals = when (currentResource) {
                is Resource.Success -> currentResource.data
                else -> emptyList()
            }
            val updatedHabits = currentGoals.toMutableList().apply { remove(goal) }
            _goalState.value = Resource.Success(updatedHabits)
        }
    }
}