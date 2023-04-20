package com.santrucho.habilife.ui.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.domain.habit.*
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val getHabitUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val getCompletedDatesUseCase: GetCompletedDatesUseCase,
    private val finishHabitUseCase: FinishHabitUseCase,
    private val getHabitOptionUseCase: GetHabitOptionUseCase,
    private val getDaysOfWeekUseCase: GetDaysOfWeekUseCase,
    private val getHabitCompleteUseCase: GetHabitCompleteUseCase
) : ViewModel() {

    private val _titleValue: MutableStateFlow<String> = MutableStateFlow("")
    val titleValue = _titleValue.asStateFlow()

    private val _isEnabledConfirmButton: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEnabledConfirmButton = _isEnabledConfirmButton.asStateFlow()

    private val _habitComplete: MutableStateFlow<Int?> = MutableStateFlow(null)
    val habitComplete = _habitComplete.asStateFlow()

    private val _daysCompleted: MutableStateFlow<List<String>?> = MutableStateFlow(emptyList())
    val daysCompleted = _daysCompleted.asStateFlow()

    private val _habitType: MutableStateFlow<String> = MutableStateFlow("")
    val habitType = _habitType.asStateFlow()

    private val _finishHabit: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val finishHabit = _finishHabit.asStateFlow()

    private val _openDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val openDialog = _openDialog.asStateFlow()

    private val _limitsDays: MutableStateFlow<Int> = MutableStateFlow(16)
    private val limitsDays = _limitsDays.asStateFlow()

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
        getHabitsCompletedDates()
    }

    fun setTitleValue(value: String) {
        _titleValue.value = value
    }

    fun setOpenDialog(value: Boolean) {
        _openDialog.value = value
    }

    //Check if the confirm button can be activated, when the validations are correct
    private fun shouldEnabledConfirmButton() {
        _isEnabledConfirmButton.value = titleValue.value.isNotEmpty()
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
        _titleValue.value = ""
    }

    //Call the repository and display all habits
    fun getAllHabits() {
        viewModelScope.launch {
            _habitState.value = getHabitUseCase()
        }
    }

    //Call the options to select a type in NewHabitScreen
    private fun getOptions() {
        viewModelScope.launch {
            _options.value = getHabitOptionUseCase()
        }
    }

    private fun getDays() {
        viewModelScope.launch {
            _daysOfWeek.value = getDaysOfWeekUseCase()
        }
    }

    fun getHabitComplete() {
        viewModelScope.launch {
            _habitComplete.value = getHabitCompleteUseCase()
        }
    }

    fun getHabitsCompletedDates() {
        viewModelScope.launch {
            try {
                _daysCompleted.value = getCompletedDatesUseCase()
            } catch (e: NullPointerException) {
                _daysCompleted.value = emptyList()
            }
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
            _habitFlow.value = addHabitUseCase(title, type, frequently, timePicker, isCompleted)
            getAllHabits()
        }
    }

    //Delete an specific habit from the database
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            deleteHabitUseCase(habit)
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
            } else {
                habit.daysCompleted.remove(LocalDate.now().toString())
            }

            updateHabitUseCase(habit.id, isChecked, habit.daysCompleted)

            getHabitsCompletedDates()
            getAllHabits()
        }
    }

    fun finishHabit(habit: Habit) {
        var habitCount: Int
        if (habit.daysCompleted.size == limitsDays.value) {
            viewModelScope.launch {
                _openDialog.value = true
                _finishHabit.value = true
                habitCount = if (finishHabit.value) {
                    habitComplete.value?.plus(1) ?: 0
                } else {
                    habitComplete.value ?: 0
                }
                habitComplete.value?.let { finishHabitUseCase(habit.id, it, finishHabit.value) }
                _habitComplete.value = habitCount
            }
        }
    }

    fun extendedHabit(habit: Habit) {
        viewModelScope.launch {
            _habitComplete.value = habitComplete.value?.minus(1)
            _finishHabit.value = false
            _limitsDays.value = 32
            habitComplete.value?.let { finishHabitUseCase(habit.id, it, finishHabit.value) }
            finishHabit(habit)
        }
    }
}