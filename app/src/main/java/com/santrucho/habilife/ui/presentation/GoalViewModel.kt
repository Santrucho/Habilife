package com.santrucho.habilife.ui.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.goals.*
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.domain.goal.ExtendedGoalUseCase
import com.santrucho.habilife.ui.domain.goal.FinishGoalUseCase
import com.santrucho.habilife.ui.domain.goal.academic.AddAcademicGoalUseCase
import com.santrucho.habilife.ui.domain.goal.academic.UpdateAcademicGoalUseCase
import com.santrucho.habilife.ui.domain.goal.finance.AddFinanceGoalUseCase
import com.santrucho.habilife.ui.domain.goal.finance.UpdateFinanceGoalUseCase
import com.santrucho.habilife.ui.domain.goal.learning.AddLearningGoalUseCase
import com.santrucho.habilife.ui.domain.goal.training.AddTrainingGoalUseCase
import com.santrucho.habilife.ui.domain.goal.training.UpdateTrainingGoalUseCase
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val repository: GoalsRepository,
    private val updateAcademicGoalUseCase: UpdateAcademicGoalUseCase,
    private val updateFinanceGoalUseCase: UpdateFinanceGoalUseCase,
    private val updateTrainingGoalUseCase: UpdateTrainingGoalUseCase,
    private val addAcademicGoalUseCase: AddAcademicGoalUseCase,
    private val addLearningGoalUseCase: AddLearningGoalUseCase,
    private val addTrainingGoalUseCase: AddTrainingGoalUseCase,
    private val addFinanceGoalUseCase: AddFinanceGoalUseCase,
    private val finishGoalUseCase: FinishGoalUseCase,
    private val extendedGoalUseCase : ExtendedGoalUseCase
) : ViewModel() {

    var titleValue: MutableState<String> = mutableStateOf("")
    var descriptionValue: MutableState<String> = mutableStateOf("")

    var subjectValue: MutableState<String?> = mutableStateOf("")
    var learningValue: MutableState<Int?> = mutableStateOf(null)
    var trainingValue: MutableState<Int?> = mutableStateOf(null)
    var amountValue: MutableState<Int?> = mutableStateOf(null)

    var confirmSubject: MutableState<Boolean> = mutableStateOf(false)

    var isEditedConfirmed : MutableState<Boolean> = mutableStateOf(false)

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _openDialog: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val openDialog = _openDialog.asStateFlow()

    private val _goalComplete: MutableStateFlow<Int?> = MutableStateFlow(null)
    val goalComplete = _goalComplete.asStateFlow()

    private val _finishGoal: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val finishGoal = _finishGoal.asStateFlow()

    private val _financeFlow = MutableStateFlow<Resource<FinanceGoal>?>(null)
    val financeFlow: StateFlow<Resource<FinanceGoal>?> = _financeFlow

    private val _academicFlow = MutableStateFlow<Resource<AcademicGoal>?>(null)
    val academicFlow: StateFlow<Resource<AcademicGoal>?> = _academicFlow

    private val _learningFlow = MutableStateFlow<Resource<LearningGoal>?>(null)
    val learningFlow: StateFlow<Resource<LearningGoal>?> = _learningFlow

    private val _trainingFlow = MutableStateFlow<Resource<TrainingGoal>?>(null)
    val trainingFlow: StateFlow<Resource<TrainingGoal>?> = _trainingFlow

    private val _subjectList = MutableStateFlow<List<String>>(emptyList())
    val subjectList: StateFlow<List<String>> = _subjectList

    private val _subjectApproved = MutableStateFlow<List<String>>(emptyList())
    val subjectApproved: StateFlow<List<String>> = _subjectApproved

    private val _goalState = MutableStateFlow<Resource<List<GoalsResponse>>?>(null)
    val goalState: StateFlow<Resource<List<GoalsResponse>>?> = _goalState

    private val _goalsOptionState = MutableStateFlow<Resource<List<GoalsOption>>?>(null)
    val goalsOptionState: StateFlow<Resource<List<GoalsOption>>?> = _goalsOptionState

    init {
        getOptionsGoals()
        getGoalComplete()
    }

    //Check if the confirm button can be activated, when the validations are correct
    fun shouldEnabledConfirmButton() : Boolean {
        if(titleValue.value.isNotBlank()){
            isEnabledConfirmButton.value = true
        }
        return isEnabledConfirmButton.value
    }
    //Call academic Repository and add goal into the database
    private fun addAcademicGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        subject: String,
        subjectList: List<String>,
        subjectApproved: List<String>
    ) {
        viewModelScope.launch {
            _academicFlow.value = Resource.Loading()
            _academicFlow.value = addAcademicGoalUseCase(
                title,
                description,
                isCompleted,
                release_date,
                subject,
                subjectList,
                subjectApproved
            )
        }
    }

    //Call learning Repository and add goal into the database
    private fun addLearningGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        timesAWeek: Int
    ) {
        viewModelScope.launch {
            _learningFlow.value = Resource.Loading()
            _learningFlow.value =
                addLearningGoalUseCase(title, description, isCompleted, release_date, timesAWeek)
        }
    }

    private fun addFinanceGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        amount: Int?,
        amountGoal: Int?,
    ) {
        viewModelScope.launch {
            _financeFlow.value = Resource.Loading()
            _financeFlow.value = addFinanceGoalUseCase(
                title,
                description,
                isCompleted,
                release_date,
                amount,
                amountGoal
            )
        }
    }

    private fun addTrainingGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        kilometers: Int?,
        kilometersGoal: Int?
    ) {
        viewModelScope.launch {
            _trainingFlow.value = Resource.Loading()
            _trainingFlow.value = addTrainingGoalUseCase(
                title,
                description,
                isCompleted,
                release_date,
                kilometers,
                kilometersGoal
            )
        }
    }

    //Call to the repository and add a Goal into the database
    fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        type: String,
        amount: Int? = 0,
        amountGoal: Int? = null,
        subject: String = "",
        subjectList: List<String> = emptyList(),
        subjectApproved: List<String> = emptyList(),
        timesAWeek: Int = 0,
        kilometers: Int? = null,
        kilometersGoal: Int? = null
    ) {
        viewModelScope.launch {
            when (type) {
                "Finance" -> {
                    addFinanceGoal(
                        title,
                        description,
                        isCompleted,
                        release_date,
                        amount,
                        amountGoal
                    )
                }
                "Academic" -> {
                    addAcademicGoal(
                        title,
                        description,
                        isCompleted,
                        release_date,
                        subject,
                        subjectList,
                        subjectApproved
                    )
                }
                "Learning" -> {
                    addLearningGoal(title, description, isCompleted, release_date, timesAWeek)
                }
                "Training" -> {
                    addTrainingGoal(
                        title,
                        description,
                        isCompleted,
                        release_date,
                        kilometers,
                        kilometersGoal
                    )
                }
                else -> {
                    Unit
                }
            }
        }
    }

    //Reset the result of each fields
    fun resetResult() {
        _financeFlow.value = null
        _academicFlow.value = null
        _learningFlow.value = null
        _trainingFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
        subjectValue.value = ""
        _subjectList.value = emptyList()
        amountValue.value = null
        learningValue.value = null
        trainingValue.value = null
        confirmSubject.value = false
    }

    //Call the repository and display all goals
    fun getAllGoals() {
        viewModelScope.launch {
            _goalState.value = repository.getGoals()
        }
    }

    fun finishGoal(goal: GoalsResponse) {
        if (finishGoal.value) {
            viewModelScope.launch {
                goalComplete.value?.let { finishGoalUseCase(goal.id) }
            }
        }
        deleteGoal(goal)
    }

    fun extendedGoal(goal: GoalsResponse, newDate: String) {
        viewModelScope.launch {
            _finishGoal.value = false
            extendedGoalUseCase(goal.id,newDate)
        }
    }


    fun updateGoal(
        goal: GoalsResponse,
        amount: Int?,
        newAmount: Int?,
        kilometers: Int?,
        addKilometers: Int?,
        subjectApproved: List<String>
    ) {
        viewModelScope.launch {
            updateAcademicGoalUseCase(goal.id, subjectApproved)
            updateFinanceGoalUseCase(goal.id, (amount ?: 0) + (newAmount ?: 0))
            updateTrainingGoalUseCase(goal.id, (kilometers ?: 0) + (addKilometers ?: 0))
            getAllGoals()
            amountValue.value = null
            trainingValue.value = 0
            subjectValue.value = null
        }
    }

    //Delete an specific goal from the database
    fun deleteGoal(goal: GoalsResponse) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
            val currentGoals = when (val currentResource = _goalState.value) {
                is Resource.Success -> currentResource.data
                else -> emptyList()
            }
            val updatedHabits = currentGoals.toMutableList().apply { remove(goal) }
            _goalState.value = Resource.Success(updatedHabits)
        }
    }

    fun getOptionsGoals() {
        viewModelScope.launch {
            _goalsOptionState.value = repository.getGoalsOptions()
        }
    }

    fun addSubject(subjectValue: MutableState<String?>) {
        val currentList = _subjectList.value.toMutableList()
        currentList.add(subjectValue.value ?: "")
        _subjectList.value = currentList
    }

    fun deleteSubject(subject: String?) {
        val currentList = _subjectList.value.toMutableList()
        currentList.remove(subject ?: "")
        _subjectList.value = currentList
    }

    fun subjectApproved(subjectValue: String?) {
        val approvedList = _subjectApproved.value.toMutableList()
        approvedList.add(subjectValue ?: "")
        _subjectApproved.value = approvedList
    }

    fun updateSubjectApproved(subjectValue: String?) {
        val approvedList = _subjectApproved.value.toMutableList()
        approvedList.remove(subjectValue ?: "")
        _subjectApproved.value = approvedList
    }

    fun getGoalComplete() {
        viewModelScope.launch {
            _goalComplete.value = repository.getGoalsCompleted()
        }
    }
}
