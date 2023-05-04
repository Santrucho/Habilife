package com.santrucho.habilife.ui.ui.goals.addgoal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.ReleaseDatePicker
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddGoal(goalViewModel: GoalViewModel, navController: NavController, type: String) {

    val context = LocalContext.current
    val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)


    val financeState = goalViewModel.financeFlow.collectAsState()
    val academicState = goalViewModel.academicFlow.collectAsState()
    val learningState = goalViewModel.learningFlow.collectAsState()
    val trainingState = goalViewModel.trainingFlow.collectAsState()

    //Pick date
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MM yyyy").format(pickedDate)
        }
    }

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(Screen.NewGoalScreen.route) }
    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.90f)
                ) {
                    Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),text = "Objetivo",fontSize = 24.sp)
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        elevation = 3.dp,
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            OutlinedTextField(
                                value = TextFieldValue(goalViewModel.titleValue.value),
                                onValueChange = { goalViewModel.titleValue.value = it.text},
                                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                                placeholder = {Text(text = stringResource(id = goalTypeHelper(type)))},
                                shape = RoundedCornerShape(4.dp),
                                singleLine = true,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Descripcion",fontSize = 24.sp)
                        Text(text = "*opcional",fontSize = 12.sp, color = Color.Gray)
                    }

                    Card(
                        shape = MaterialTheme.shapes.medium,
                        elevation = 3.dp,
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            CustomDescriptionFields(
                                placeholder = stringResource(id = descriptionTypeHelper(type)),
                                shape = RoundedCornerShape(4.dp),
                                value = goalViewModel.descriptionValue.value,
                                valueChange = { goalViewModel.descriptionValue.value = it },
                                onValidate = { })
                        }
                    }

                    ReleaseDatePicker(pickDate = pickedDate, onDatePicked = { date ->
                        pickedDate = date
                    })
                    FieldsHelper(type, goalViewModel)
                    Spacer(modifier = Modifier.weight(1f))
                }

                //Set the button to add the goal into database
                Button(
                    onClick = {
                        goalViewModel.subjectValue.value?.let { it1 ->
                            goalViewModel.addGoal(
                                goalViewModel.titleValue.value,
                                goalViewModel.descriptionValue.value,
                                isCompleted = false,
                                release_date = formattedDate,
                                type = type,
                                amountGoal = goalViewModel.amountValue.value,
                                subject = it1,
                                subjectList = goalViewModel.subjectList.value,
                                kilometersGoal = goalViewModel.trainingValue.value,
                                timesAWeek = 4
                            )
                        }
                        LogBundle.logBundleAnalytics(
                            firebaseAnalytics,
                            "Add Goal",
                            "add_goal_pressed"
                        )
                    },
                    enabled = goalViewModel.shouldEnabledConfirmButton(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(56.dp),
                    shape = CircleShape

                ) {
                    Text("Guardar objetivo")
                }
            }
            HandleState(
                flow = financeState,
                navController = navController,
                route = BottomNavScreen.Goals.screen_route,
                text = "Objetivo creado",
                message = "Finance Goal",
                eventName = "add_finance_goal"
            )
            HandleState(
                flow = academicState,
                navController = navController,
                route = BottomNavScreen.Goals.screen_route,
                text = "Objetivo creado",
                message = "Academic Goal",
                eventName = "add_academic_goal"
            )
            HandleState(
                flow = trainingState,
                navController = navController,
                route = BottomNavScreen.Goals.screen_route,
                text = "Objetivo creado",
                message = "Training Goal",
                eventName = "add_training_goal"
            )
            HandleState(
                flow = learningState,
                navController = navController,
                route = BottomNavScreen.Goals.screen_route,
                text = "Objetivo creado",
                message = "Learning Goal",
                eventName = "add_learning_goal"
            )
        }
    }
}

fun goalTypeHelper(type:String) : Int{
    return when(type){
        "Academic" ->{
            R.string.goal_placeholder_academic
        }
        "Learning" -> {
            R.string.goal_placeholder_learning
        }
        "Training" -> {
            R.string.goal_placeholder_training
        }
        else -> {
            R.string.goal_placeholder_finance
        }
    }
}
fun descriptionTypeHelper(type:String) : Int{
    return when(type){
        "Academic" ->{
            R.string.description_academic
        }
        "Learning" -> {
            R.string.description_learning
        }
        "Training" -> {
            R.string.description_training
        }
        else -> {R.string.description_finance}
    }
}