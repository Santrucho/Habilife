package com.santrucho.habilife.ui.presentation

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.model.HabitResponse
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository : HabitsRepository) : ViewModel() {


    private val _habitFlow = MutableStateFlow<Resource<Habit>?>(null)
    val habitFlow : StateFlow<Resource<Habit>?> = _habitFlow

    private val _habitState : MutableState<HabitResponse> = mutableStateOf(HabitResponse())
    val habitState : State<HabitResponse> = _habitState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    init {
        getAllHabits()
    }

    fun addHabit(id:String,title:String,description:String,image:String,frequently:String,isCompleted:Boolean,isExpanded:Boolean){
        viewModelScope.launch {
            _habitFlow.value = Resource.Loading()
            val createHabit = repository.addHabit(id,title,description,image,frequently,isCompleted,isExpanded)
            _habitFlow.value = createHabit
        }

    }

    fun getAllHabits(){
        viewModelScope.launch {
            repository.getHabits().let{ resource ->
                when(resource){
                    is Resource.Loading -> {
                        _habitState.value = HabitResponse(isLoading = true)
                    }
                    is Resource.Success -> {
                        _habitState.value = HabitResponse(listHabits = resource.data ?: emptyList())
                    }
                    is Resource.Failure -> {
                        _habitState.value = HabitResponse(error = resource.exception.message ?: "Error inesperado")
                    }
                }
            }
        }
    }

}