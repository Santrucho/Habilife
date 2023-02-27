package com.santrucho.habilife.ui.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.Goals
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository:GoalsRepository,
                                        private val academicRepo : AcademicGoalRepository,
                                        private val financeRepo: FinanceGoalRepository) : ViewModel() {

    var titleValue : MutableState<String> = mutableStateOf("")
    var isTitleValid: MutableState<Boolean> = mutableStateOf(false)
    var titleErrMsg: MutableState<String> = mutableStateOf("")

    var subjectValue : MutableState<String> = mutableStateOf("")
    var amountValue : MutableState<Int?> = mutableStateOf(null)

    var descriptionValue: MutableState<String> = mutableStateOf("")
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    var descriptionErrMsg: MutableState<String> = mutableStateOf("")

    var isEnabledConfirmButton: MutableState<Boolean> = mutableStateOf(false)

    private val _goalFlow = MutableStateFlow<Resource<FinanceGoal>?>(null)
    val goalFlow : StateFlow<Resource<FinanceGoal>?> = _goalFlow

    private val _academicFlow = MutableStateFlow<Resource<AcademicGoal>?>(null)
    val academicFlow : StateFlow<Resource<AcademicGoal>?> = _academicFlow

    private val _goalState = MutableStateFlow<Resource<List<Goals>>?>(null)
    val goalState : StateFlow<Resource<List<Goals>>?> = _goalState

    private val _goalsOptionState = MutableStateFlow<Resource<List<GoalsOption>>?>(null)
    val goalsOptionState : StateFlow<Resource<List<GoalsOption>>?> = _goalsOptionState

    init {
        getOptionsGoals()
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
    //Call academic Repository and add goal into the database
    private fun addAcademicGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        subject: String,
        subjectGoal: Int,
        subjectApprove : Int,
    ){
        viewModelScope.launch {
            _academicFlow.value = Resource.Loading()
            _academicFlow.value = academicRepo.addAcademicGoal(title,description,isCompleted,release_date,subject,subjectGoal,subjectApprove)
            Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",_academicFlow.value.toString())
            Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",_academicFlow.value.toString())
            Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",_academicFlow.value.toString())
        }
            getAllGoals()
    }
    private fun addFinanceGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        amount:Int?,
        amountGoal:String,
    ){
        viewModelScope.launch {
            _goalFlow.value = Resource.Loading()
            _goalFlow.value = financeRepo.addFinanceGoal(title,description,isCompleted,release_date,amount,amountGoal)
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",_academicFlow.value.toString())
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",_academicFlow.value.toString())
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",_academicFlow.value.toString())
        }
        getAllGoals()
    }
    //Call to the repository and add a Goal into the database
    fun addGoal(
        title: String,
        description: String,
        isCompleted: Boolean,
        release_date: String,
        type:String,
        amount: Int? = null,
        amountGoal: String = "",
        subject: String = "",
        subjectGoal: Int = 0,
        subjectApprove : Int = 0,
    ) {
        viewModelScope.launch {
            _goalFlow.value = Resource.Loading()
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
                        subjectGoal,
                        subjectApprove
                    )
                }
                else -> {
                    Unit
                }
            }
            getAllGoals()
        }
    }
    //Reset the result of each fields
    fun resetResult(){
        _goalFlow.value = null
        _academicFlow.value = null
        titleValue.value = ""
        descriptionValue.value = ""
        subjectValue.value = ""
        amountValue.value = null
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
    fun deleteGoal(goal: Goals){
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
