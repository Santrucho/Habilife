package com.santrucho.habilife.ui.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.*
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

    var titleValue : MutableState<String> = mutableStateOf("")
    var isTitleValid: MutableState<Boolean> = mutableStateOf(false)
    var titleErrMsg: MutableState<String> = mutableStateOf("")

    var descriptionValue: MutableState<String> = mutableStateOf("")
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    var descriptionErrMsg: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    var habitComplete: MutableState<Int?> = mutableStateOf(null)
    var daysCompleted : MutableState<List<String>> = mutableStateOf(emptyList())

    var coloredDay : MutableState<Boolean> = mutableStateOf(false)

    private val _habitState = MutableStateFlow<Resource<List<Habit>>?>(null)
    val habitState: StateFlow<Resource<List<Habit>>?> = _habitState

    private val _habitFlow = MutableStateFlow<Resource<Habit>?>(null)
    val habitFlow: StateFlow<Resource<Habit>?> = _habitFlow

    private val _options = MutableStateFlow<Resource<List<String>>?>(null)
    val options: StateFlow<Resource<List<String>>?> = _options

    private val _daysOfWeek = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val daysOfWeek : StateFlow<Resource<List<String>>> = _daysOfWeek

    init {
        getOptions()
        getDays()
        getHabitComplete()
        getHabitsDateCompleted()
    }
    //Check if the confirm button can be activated, when the validations are correct
    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            titleErrMsg.value.isEmpty()
                && descriptionErrMsg.value.isEmpty()
                && !titleValue.value.isNullOrBlank()
                && !descriptionValue.value.isNullOrBlank()
    }

    //Check if the title is valid
    fun validateTitle() {
        if (titleValue.value.length >= 20) {
            isTitleValid.value = true
            titleErrMsg.value = "Title should be less than 20 chars"
        } else {
            isTitleValid.value = false
            titleErrMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    //Check if the description is valid
    fun validateDescription() {
        if (descriptionValue.value.length <= 5) {
            isDescriptionValid.value = true
            descriptionErrMsg.value = "Description should be long than 5 chars"
        } else {
            isDescriptionValid.value = false
            descriptionErrMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    //Reset the result of each fields
    fun resetResult(){
        _habitFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
    }

    //Call the options to select a type in NewHabitScreen
    private fun getOptions(){
        viewModelScope.launch {
            _options.value = repository.getOptions()
        }
    }

    private fun getDays(){
        viewModelScope.launch {
            _daysOfWeek.value = repository.getDaysOfWeek()
        }
    }

    //Call the repository and display all habits
    fun getAllHabits(){
        viewModelScope.launch {
            _habitState.value = repository.getHabits()
        }
    }

    //Call to the repository and add a Habit into the database
    fun addHabit(
        title: String,
        description: String,
        type: String,
        frequently: List<String>,
        timePicker: String,
        isCompleted: Boolean,
    ) {

        viewModelScope.launch {
            _habitFlow.value = Resource.Loading()
            _habitFlow.value = repository.addHabit(title, description, type, frequently,timePicker,isCompleted)
            getAllHabits()
        }
    }

    //Delete an specific habit from the database
    fun deleteHabit(habit:Habit){
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

    fun onCompleted(habit:Habit,isChecked:Boolean){
        viewModelScope.launch {
            var newHabitCount = 0
            if (isChecked) {
                habit.daysCompleted.add(LocalDate.now().toString())
                newHabitCount = habitComplete.value?.plus(1) ?: 0
            } else {
                habit.daysCompleted.remove(LocalDate.now().toString())
                newHabitCount = habitComplete.value ?: 0
            }
            repository.updateHabit(habit.id,isChecked,newHabitCount,habit.daysCompleted)
            habitComplete.value = newHabitCount
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
}