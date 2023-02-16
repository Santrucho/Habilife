package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository : GoalsRepository) : ViewModel() {

    var titleValue : MutableState<String> = mutableStateOf("")
    var isTitleValid: MutableState<Boolean> = mutableStateOf(false)
    var titleErrMsg: MutableState<String> = mutableStateOf("")

    var descriptionValue: MutableState<String> = mutableStateOf("")
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    var descriptionErrMsg: MutableState<String> = mutableStateOf("")

    var release_dateValue: MutableState<String> = mutableStateOf("")
    var isreleaseDateValid: MutableState<Boolean> = mutableStateOf(false)
    var release_dateMsg: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _goalFlow = MutableStateFlow<Resource<Goals>?>(null)
    val goalFlow : StateFlow<Resource<Goals>?> = _goalFlow

    private val _goalState = MutableStateFlow<Resource<List<Goals>>?>(null)
    val goalState : StateFlow<Resource<List<Goals>>?> = _goalState

    //Check if the confirm button can be activated, when the validations are correct
    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            titleErrMsg.value.isEmpty()
                    && descriptionErrMsg.value.isEmpty()
                    && release_dateMsg.value.isEmpty()
                    && !titleValue.value.isNullOrBlank()
                    && !descriptionValue.value.isNullOrBlank()
                    && !release_dateValue.value.isNullOrBlank()
    }
    //Check if the title is valid
    fun validateTitle() {
        if (titleValue.value.length >= 10) {
            isTitleValid.value = true
            titleErrMsg.value = "Title should be less than 10 chars"
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
    //Check if the release date is valid
    fun validateReleaseDate() {
        if (release_dateValue.value.length <= 6) {
            isreleaseDateValid.value = true
            release_dateMsg.value = "Release date should be 6 digit in format dd-mm-yyyy"
        } else {
            isreleaseDateValid.value = false
            release_dateMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }
    //Call to the repository and add a Goal into the database
    fun addGoal(title:String,description:String,isCompleted:Boolean,release_date:String){
        viewModelScope.launch {
            _goalFlow.value = Resource.Loading()
            _goalFlow.value = repository.addGoal(title,description,isCompleted,release_date)
           getAllGoals()
        }
    }
    //Reset the result of each fields
    fun resetResult(){
        _goalFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
        release_dateValue.value = ""
    }
    //Reset the response for each call to get the goals in the database
    fun resetValue(){
        _goalState.value = null
    }
    //Call the repository and display all goals
    fun getAllGoals(){
        viewModelScope.launch {
            _goalState.value = repository.getGoals()
        }
    }

    //Delete an specific goal from the database
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