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
import com.santrucho.habilife.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    var confirmSubject : MutableState<Boolean> = mutableStateOf(false)

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    var goalComplete: MutableState<Int?> = mutableStateOf(null)

    private val _financeFlow = MutableStateFlow<Resource<FinanceGoal>?>(null)
    val financeFlow : StateFlow<Resource<FinanceGoal>?> = _financeFlow

    private val _academicFlow = MutableStateFlow<Resource<AcademicGoal>?>(null)
    val academicFlow : StateFlow<Resource<AcademicGoal>?> = _academicFlow

    private val _subjectList = MutableStateFlow<List<String>>(emptyList())
    val subjectList : StateFlow<List<String>> = _subjectList

    private val _subjectApproved = MutableStateFlow<List<String>>(emptyList())
    val subjectApproved : StateFlow<List<String>> = _subjectApproved

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
        if (textRegex.matches(titleValue.value)) {
            isTitleValid.value = false
            titleErrMsg.value = ""
        } else {
            isTitleValid.value = true
            titleErrMsg.value = "No se pueden utilizar simbolos"
        }
        shouldEnabledConfirmButton()
    }

    fun validateDescription(){
        val textRegex = Regex("^[A-Z][a-zA-Z0-9 ]*$")
        if (textRegex.matches(descriptionValue.value)) {
            isDescriptionValid.value = false
            descriptionErrMsg.value = ""
        } else {
            isDescriptionValid.value = true
            descriptionErrMsg.value = "No se pueden utilizar simbolos"
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
        subjectList : List<String>?,
        subjectApproved:List<String>?
    ){
        viewModelScope.launch {
            _academicFlow.value = Resource.Loading()
            _academicFlow.value = academicRepo.addAcademicGoal(title,description,isCompleted,release_date,subject,subjectList,subjectApproved)
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

    private fun addRunningGoal(title: String,
                            description: String,
                            isCompleted: Boolean,
                            release_date: String,
                            kilometers:Int?,
                            kilometersGoal:Int?){
        viewModelScope.launch {
            _trainingFlow.value = Resource.Loading()
            _trainingFlow.value = trainingRepo.addRunningGoal(title,description,isCompleted,release_date,kilometers,kilometersGoal)
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
        subjectList : List<String>? = emptyList(),
        subjectApproved: List<String> = emptyList(),
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
                   addAcademicGoal(title, description, isCompleted, release_date, subject,subjectList,subjectApproved)
                }
                "Learning" -> {
                    addLearningGoal(title,description,isCompleted,release_date, timesAWeek)
                }
                "Training" -> {
                    addRunningGoal(title,description,isCompleted,release_date, kilometers, kilometersGoal)
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
        _subjectList.value = emptyList()
        amountValue.value = null
        learningValue.value = null
        trainingValue.value = null
        confirmSubject.value = false
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

    fun updateGoal(goal:GoalsResponse,amount:Int?,newAmount:Int?,kilometers: Int?,addKilometers: Int?,subjectApproved:List<String>){
        viewModelScope.launch {
            financeRepo.updateGoal(goal.id, (amount ?: 0) + (newAmount ?: 0))
            trainingRepo.updateGoal(goal.id,(kilometers ?: 0) + (addKilometers ?: 0))
            academicRepo.updateGoal(goal.id,subjectApproved)
            getAllGoals()
            amountValue.value = null
            trainingValue.value = 0
            subjectValue.value = null
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

    fun addSubject(subjectValue: MutableState<String?>) {
        val currentList = _subjectList.value.toMutableList()
        currentList.add(subjectValue.value ?: "")
        _subjectList.value = currentList
    }
    fun deleteSubject(subject:String?){
        val currentList = _subjectList.value.toMutableList()
        currentList.remove(subject ?: "")
        _subjectList.value = currentList
    }

    fun subjectApproved(subjectValue: String?){
        val approvedList = _subjectApproved.value.toMutableList()
        approvedList.add(subjectValue ?: "")
        _subjectApproved.value = approvedList
    }

    fun updateSubjectApproved(subjectValue: String?){
        val approvedList = _subjectApproved.value.toMutableList()
        approvedList.remove(subjectValue ?: "")
        _subjectApproved.value = approvedList
    }
}
