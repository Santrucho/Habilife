package com.santrucho.habilife.ui.ui.goals.addgoal

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.FieldsHelper
import com.santrucho.habilife.ui.utils.HandleState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddGoal(goalViewModel: GoalViewModel, navController: NavController,type:String) {

    val financeValue = goalViewModel.financeFlow.collectAsState()
    val academicValue = goalViewModel.academicFlow.collectAsState()
    //val workValue = goalViewModel.workFlow.collectAsState()
    val trainingValue = goalViewModel.trainingFlow.collectAsState()
    val learningValue = goalViewModel.learningFlow.collectAsState()

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(Screen.NewGoalScreen.route) }
    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack,"Crear objetivo") }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                /*ReleaseDatePicker(pickDate = pickedDate, onDatePicked = {date->
                    pickedDate = date
                } )*/
                Card(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 3.dp,
                    backgroundColor = MaterialTheme.colors.background,
                    modifier = Modifier.padding(4.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text("Personalice su objetivo", fontSize = 20.sp, color = Color.Black)
                        NewFields(text = "Nombre del objetivo",
                            value = goalViewModel.titleValue.value ?: "",
                            isError = goalViewModel.isTitleValid.value,
                            error = goalViewModel.titleErrMsg.value,
                            valueChange = { goalViewModel.titleValue.value = it },
                            onValidate = { goalViewModel.validateTitle() })

                        NewFields(text = "Descripcion",
                            value = goalViewModel.descriptionValue.value,
                            isError = goalViewModel.isDescriptionValid.value,
                            error = goalViewModel.descriptionErrMsg.value,
                            valueChange = {goalViewModel.descriptionValue.value =  it },
                            onValidate = { goalViewModel.validateDescription() })

                        OutlinedTextField(
                            value = type,
                            onValueChange = {},
                            enabled = false,
                            label = { Text(text = "Tipo") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            shape = CircleShape,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Gray,
                                unfocusedLabelColor = Color.Black)
                        )
                    }
                }

                FieldsHelper(type,goalViewModel)
                Spacer(modifier = Modifier.weight(1f))

                //Set the button to add the goal into database
                Button(
                    onClick = {
                        goalViewModel.addGoal(
                            goalViewModel.titleValue.value,
                            goalViewModel.descriptionValue.value,
                            isCompleted = false,
                            release_date = "25/02",
                            type = type,
                            amountGoal = goalViewModel.amountValue.value,
                            subject = goalViewModel.subjectValue.value,
                            subjectList = goalViewModel.subjectList.value,
                            kilometersGoal = goalViewModel.trainingValue.value,
                            timesAWeek = 4
                        )
                    },
                    enabled = goalViewModel.isEnabledConfirmButton.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(56.dp),
                    shape = CircleShape

                ) {
                    Text("Guardar objetivo")
                }
                Spacer(modifier = Modifier.padding(56.dp))
            }
            HandleState(flow = financeValue, navController = navController, route = BottomNavScreen.Goals.screen_route, text= "Objetivo creado")
            HandleState(flow = academicValue, navController = navController,route = BottomNavScreen.Goals.screen_route, text = "Objetivo creado")
            //HandleState(flow = workValue , navController = navController,route = BottomNavScreen.Goals.screen_route,text ="Objetivo creado")
            HandleState(flow = trainingValue , navController = navController,route = BottomNavScreen.Goals.screen_route,text ="Objetivo creado")
            HandleState(flow = learningValue , navController = navController,route = BottomNavScreen.Goals.screen_route,text ="Objetivo creado")
        }
    }
}