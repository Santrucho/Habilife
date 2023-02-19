package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.model.ItemList
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitsRepository) : ViewModel() {

    var titleValue : MutableState<String> = mutableStateOf("")
    var isTitleValid: MutableState<Boolean> = mutableStateOf(false)
    var titleErrMsg: MutableState<String> = mutableStateOf("")

    var descriptionValue: MutableState<String> = mutableStateOf("")
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    var descriptionErrMsg: MutableState<String> = mutableStateOf("")

    var frequencyValue: MutableState<String> = mutableStateOf("")
    var isFrequentlyValid: MutableState<Boolean> = mutableStateOf(false)
    var frequentlyMsg: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _habitState = MutableStateFlow<Resource<List<Habit>>?>(null)
    val habitState: StateFlow<Resource<List<Habit>>?> = _habitState

    private val _habitFlow = MutableStateFlow<Resource<Habit>?>(null)
    val habitFlow: StateFlow<Resource<Habit>?> = _habitFlow

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
        frequencyValue.value = ""
    }

    //Reset the response for the call to get the habits in the database
    fun resetValue(){
        _habitState.value = null
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
        isExpanded: Boolean
    ) {

        viewModelScope.launch {
            _habitFlow.value = Resource.Loading()
            _habitFlow.value = repository.addHabit(title, description, type, frequently,timePicker,isCompleted, isExpanded)
            getAllHabits()
        }
    }

    //Delete an specific habit from the database
    fun deleteHabit(habit:Habit){
        viewModelScope.launch {
            repository.deleteHabit(habit)
            val currentResource = _habitState.value
            val currentHabits = when (currentResource) {
                is Resource.Success -> currentResource.data
                else -> emptyList()
            }
            val updatedHabits = currentHabits.toMutableList().apply { remove(habit) }
            _habitState.value = Resource.Success(updatedHabits)
        }
    }
}