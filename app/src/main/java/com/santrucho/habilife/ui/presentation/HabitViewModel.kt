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
class HabitViewModel @Inject constructor(private val repository: HabitsRepository) : ViewModel() {

    //val habit : Habit = Habit()

    //var isLoading : MutableState<Boolean> = mutableStateOf(false)

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

    private val _habitFlow = MutableStateFlow<Resource<Habit>?>(null)
    val habitFlow: StateFlow<Resource<Habit>?> = _habitFlow

    private val _habitState: MutableState<HabitResponse> = mutableStateOf(HabitResponse())
    val habitState: State<HabitResponse> = _habitState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    val error = MutableStateFlow<String>("")

    init {
        getAllHabits()
    }

    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            titleErrMsg.value.isEmpty()
                && descriptionErrMsg.value.isEmpty()
                && frequentlyMsg.value.isEmpty()
                && !titleValue.value.isNullOrBlank()
                && !descriptionValue.value.isNullOrBlank()
                && !frequencyValue.value.isNullOrBlank()
    }

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

    fun validateFrequently() {
        if (frequencyValue.value.length <= 6) {
            isFrequentlyValid.value = true
            frequentlyMsg.value = "Description should be 6 digit in format dd-mm-yyyy"
        } else {
            isFrequentlyValid.value = false
            frequentlyMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    fun addHabit(
        id: String,
        title: String,
        description: String,
        image: String,
        frequently: String,
        isCompleted: Boolean,
        isExpanded: Boolean
    ) {
        viewModelScope.launch {
            _habitFlow.value = Resource.Loading()
            val result = repository.addHabit(id, title, description, image, frequently, isCompleted, isExpanded)
            _habitFlow.value = result
        }
    }

    fun resetResult(){
        _habitFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
        frequencyValue.value = ""
    }

    fun getAllHabits() {
        viewModelScope.launch {
            repository.getHabits().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _habitState.value = HabitResponse(isLoading = true)
                    }
                    is Resource.Success -> {
                        _habitState.value = HabitResponse(listHabits = resource.data)
                    }
                    is Resource.Failure -> {
                        _habitState.value =
                            HabitResponse(error = resource.exception.message ?: "Error inesperado")
                    }
                }
            }
        }
    }
}