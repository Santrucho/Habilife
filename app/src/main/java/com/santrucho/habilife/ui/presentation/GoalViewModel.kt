package com.santrucho.habilife.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.goals.*
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.learning.LearningRepository
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.work.WorkGoalRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository:GoalsRepository,
                                        private val academicRepo : AcademicGoalRepository,
                                        private val financeRepo: FinanceGoalRepository,
                                        private val workRepo:WorkGoalRepository,
                                        private val trainingRepo:TrainingGoalRepository,
                                        private val learningRepo:LearningRepository) : ViewModel() {

    var titleValue : MutableState<String> = mutableStateOf("")
    var isTitleValid : MutableState<Boolean> = mutableStateOf(false)
    var titleErrMsg : MutableState<String> = mutableStateOf("")

    var descriptionValue: MutableState<String> = mutableStateOf("")
    var isDescriptionValid : MutableState<Boolean> = mutableStateOf(false)
    var descriptionErrMsg : MutableState<String> = mutableStateOf("")

    var subjectValue : MutableState<String?> = mutableStateOf("")
    var learningValue : MutableState<Int?> = mutableStateOf(null)
    var trainingValue : MutableState<Int?> = mutableStateOf(null)
    var amountValue : MutableState<Int?> = mutableStateOf(null)

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _financeFlow = MutableStateFlow<Resource<FinanceGoal>?>(null)
    val financeFlow : StateFlow<Resource<FinanceGoal>?> = _financeFlow

    private val _academicFlow = MutableStateFlow<Resource<AcademicGoal>?>(null)
    val academicFlow : StateFlow<Resource<AcademicGoal>?> = _academicFlow

    private val _learningFlow = MutableStateFlow<Resource<LearningGoal>?>(null)
    val learningFlow : StateFlow<Resource<LearningGoal>?> = _learningFlow

    //private val _workFlow = MutableStateFlow<Resource<WorkGoal>?>(null)
    //val workFlow : StateFlow<Resource<WorkGoal>?> = _workFlow

    private val _trainingFlow = MutableStateFlow<Resource<TrainingGoal>?>(null)
    val trainingFlow : StateFlow<Resource<TrainingGoal>?> = _trainingFlow

    private val _goalState = MutableStateFlow<Resource<List<GoalsResponse>>?>(null)
    val goalState : StateFlow<Resource<List<GoalsResponse>>?> = _goalState

    private val _goalsOptionState = MutableStateFlow<Resource<List<GoalsOption>>?>(null)
    val goalsOptionState : StateFlow<Resource<List<GoalsOption>>?> = _goalsOptionState

    init {
        getOptionsGoals()
    }

    //Check if the confirm button can be activated, when the validations are correct
    private fun shouldEnabledConfirmButton() {
        isEnabledConfirmButton.value =
            titleErrMsg.value.isEmpty() && descriptionErrMsg.value.isEmpty() &&
                    titleValue.value.isNotBlank()
                    && descriptionValue.value.isNotBlank()
    }

    fun validateTitle() {
        val textRegex = Regex("^[A-Z][a-zA-Z0-9 ]*$")
        if (textRegex.matches(titleValue.value) && textRegex.matches(descriptionValue.value)) {
            isTitleValid.value = true
            titleValue.value = "No se pueden utilizar simbolos"
        } else {
            isTitleValid.value = false
            titleErrMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    fun validateDescription(){
        val textRegex = Regex("^[A-Z][a-zA-Z0-9 ]*$")
        if (textRegex.matches(descriptionValue.value)) {
            isDescriptionValid.value = true
            descriptionValue.value = "No se pueden utilizar simbolos"
        } else {
            isDescriptionValid.value = false
            descriptionErrMsg.value = ""
        }
        shouldEnabledConfirmButton()
    }

    //Call academic Repository and add goal into the database
    private fun addAcademicGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        subject: String?,
        subjectGoal: Int,
        subjectApprove : Int,
    ){
        viewModelScope.launch {
            _academicFlow.value = Resource.Loading()
            _academicFlow.value = academicRepo.addAcademicGoal(title,description,isCompleted,release_date,subject,subjectGoal,subjectApprove)
        }
    }
    //Call learning Repository and add goal into the database
    private fun addLearningGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        timesAWeek: Int
    ){
        viewModelScope.launch {
            _learningFlow.value = Resource.Loading()
            _learningFlow.value = learningRepo.addLearningGoal(title,description,isCompleted,release_date,timesAWeek)
        }
    }
    private fun addFinanceGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        amount:Int?,
        amountGoal:Int?,
    ){
        viewModelScope.launch {
            _financeFlow.value = Resource.Loading()
            _financeFlow.value = financeRepo.addFinanceGoal(title,description,isCompleted,release_date,amount,amountGoal)
        }
    }

    /*private fun addWorkGoal(title: String,
                            description: String,
                            isCompleted: Boolean,
                            release_date: String,
                            actualJob:String?,
                            jobGoal:String){
        viewModelScope.launch {
            _workFlow.value = Resource.Loading()
            _workFlow.value = workRepo.addWorkGoal(title,description,isCompleted,release_date,actualJob,jobGoal)
        }
    } */

    private fun addTrainingGoal(title: String,
                            description: String,
                            isCompleted: Boolean,
                            release_date: String,
                            kilometers:Int?,
                            kilometersGoal:Int?){
        viewModelScope.launch {
            _trainingFlow.value = Resource.Loading()
            _trainingFlow.value = trainingRepo.addTrainingGoal(title,description,isCompleted,release_date,kilometers,kilometersGoal)
        }
    }

    //Call to the repository and add a Goal into the database
    fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        type:String,
        amount: Int? = null,
        amountGoal: Int? = null,
        subject: String? = "",
        subjectGoal: Int = 0,
        subjectApprove : Int = 0,
        timesAWeek : Int = 0,
        kilometers : Int? = null,
        kilometersGoal : Int? = null
    ) {
        viewModelScope.launch {
            _financeFlow.value = Resource.Loading()
            when (type) {
                "Finance" -> {
                    addFinanceGoal(title, description, isCompleted, release_date, amount, amountGoal)
                }
                "Academic" -> {
                   addAcademicGoal(title, description, isCompleted, release_date, subject,subjectGoal, subjectApprove)
                }
                "Learning" -> {
                    addLearningGoal(title,description,isCompleted,release_date, timesAWeek)
                }
                "Training" -> {
                    addTrainingGoal(title,description,isCompleted,release_date, kilometers, kilometersGoal)
                }
                else -> {
                    Unit
                }
            }
        }
    }
    //Reset the result of each fields
    fun resetResult(){
        _financeFlow.value = null
        _academicFlow.value = null
        //_workFlow.value = null
        _trainingFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
        subjectValue.value = ""
        amountValue.value = null
        learningValue.value = null
        trainingValue.value = null
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

    fun updateGoal(goal:GoalsResponse,amount:Int?,newAmount:Int?){
        viewModelScope.launch {
            financeRepo.updateGoal(goal.id, (amount ?: 0) + (newAmount ?: 0))
            getAllGoals()
            amountValue.value = null
        }
    }

    //Delete an specific goal from the database
    fun deleteGoal(goal: GoalsResponse){
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

    fun getOptionsGoals(){
        viewModelScope.launch {
            _goalsOptionState.value = repository.getGoalsOptions()
        }
    }
}
