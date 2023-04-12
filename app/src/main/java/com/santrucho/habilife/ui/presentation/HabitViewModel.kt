package com.santrucho.habilife.ui.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitsRepository) : ViewModel() {

    var titleValue: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    var habitComplete: MutableState<Int?> = mutableStateOf(null)
    var daysCompleted: MutableState<List<String>> = mutableStateOf(emptyList())
    var habitType: MutableState<String> = mutableStateOf("")
    var finishHabit: MutableState<Boolean> = mutableStateOf(true)
    val finishedHabits = mutableStateListOf<Habit>()

    val openDialog : MutableState<Boolean> = mutableStateOf(false)

    private var limitsDays: MutableState<Int> = mutableStateOf(16)

    private val _habitState = MutableStateFlow<Resource<List<Habit>>?>(null)
    val habitState: StateFlow<Resource<List<Habit>>?> = _habitState

    private val _habitFlow = MutableStateFlow<Resource<Habit>?>(null)
    val habitFlow: StateFlow<Resource<Habit>?> = _habitFlow

    private val _options = MutableStateFlow<Resource<List<String>>?>(null)
    val options: StateFlow<Resource<List<String>>?> = _options

    private val _daysOfWeek = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val daysOfWeek: StateFlow<Resource<List<String>>> = _daysOfWeek

    init {
        getOptions()
        getDays()
        getHabitComplete()
        getHabitsDateCompleted()
    }

    //Check if the confirm button can be activated, when the validations are correct
    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value = titleValue.value.isNotEmpty()
    }

    fun validateTitle() {
        val textRegex = Regex("^[a-zA-Z][a-zA-Z0-9 ]*$")
        if (textRegex.matches(titleValue.value)) {
            shouldEnabledConfirmButton()
        }

    }

    //Reset the result of each fields
    fun resetResult() {
        _habitFlow.value = null
        titleValue.value = ""
    }

    //Call the options to select a type in NewHabitScreen
    private fun getOptions() {
        viewModelScope.launch {
            _options.value = repository.getOptions()
        }
    }

    private fun getDays() {
        viewModelScope.launch {
            _daysOfWeek.value = repository.getDaysOfWeek()
        }
    }

    //Call the repository and display all habits
    fun getAllHabits() {
        viewModelScope.launch {
            _habitState.value = repository.getHabits()
        }
    }

    //Call to the repository and add a Habit into the database
    fun addHabit(
        title: String,
        type: String,
        frequently: List<String>,
        timePicker: String,
        isCompleted: Boolean,
    ) {

        viewModelScope.launch {
            _habitFlow.value = Resource.Loading()
            _habitFlow.value =
                repository.addHabit(title, type, frequently, timePicker, isCompleted)
            getAllHabits()
        }
    }

    //Delete an specific habit from the database
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
            val currentHabits = when (val currentResource = _habitState.value) {
                is Resource.Success -> currentResource.data
                else -> emptyList()
            }
            val updatedHabits = currentHabits.toMutableList().apply { remove(habit) }
            _habitState.value = Resource.Success(updatedHabits)
        }
    }

    fun onCompleted(habit: Habit, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                habit.daysCompleted.add(LocalDate.now().toString())
                habitType.value = habit.type
            } else {
                habit.daysCompleted.remove(LocalDate.now().toString())
                habitType.value = ""
            }
            repository.updateHabit(habit.id, isChecked, habit.daysCompleted)
            getHabitsDateCompleted()
            getAllHabits()
        }
    }

    fun getHabitComplete() {
        viewModelScope.launch {
            habitComplete.value = repository.getHabitComplete()
        }
    }

    fun getHabitsDateCompleted() {
        viewModelScope.launch {
            try {
                daysCompleted.value = repository.getHabitsDateCompleted()
            } catch (e: NullPointerException) {
                daysCompleted.value = emptyList()
            }
        }
    }

    fun finishHabit(habit: Habit) {
        var habitCount = 0
        if (habit.daysCompleted.size == limitsDays.value && habit !in finishedHabits) {
            viewModelScope.launch {
                openDialog.value = true
                finishHabit.value = true
                habitCount = if(finishHabit.value){
                    habitComplete.value?.plus(1) ?: 0
                }else{
                    habitComplete.value ?: 0
                }
                finishedHabits.add(habit)
                repository.finishHabit(habit.id, habitCount, finishHabit.value)
                habitComplete.value = habitCount
            }
        }
    }

    fun extendedHabit(habit: Habit) {
        viewModelScope.launch {
            habitComplete.value = habitComplete.value?.minus(1)
            finishHabit.value = false
            limitsDays.value = 32
            repository.finishHabit(habit.id, habitComplete.value!!, finishHabit.value)
            finishHabit(habit)
        }
    }
}