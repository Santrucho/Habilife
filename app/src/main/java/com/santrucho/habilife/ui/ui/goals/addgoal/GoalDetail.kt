package com.santrucho.habilife.ui.ui.goals

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewGoalFields
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDetail(goalViewModel: GoalViewModel, navController: NavController,type:String) {

    val goalValue = goalViewModel.goalFlow.collectAsState()
    val context = LocalContext.current

    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd mm yyyy").format(pickedDate)
        }
    }

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
                NewGoalFields(goalViewModel = goalViewModel)
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
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Blue,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Blue)
                    )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        goalViewModel.addGoal(
                            goalViewModel.titleValue.value,
                            goalViewModel.descriptionValue.value, false, formattedDate
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
            }
            goalValue.value?.let {
                when (it) {
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(BottomNavScreen.Goals.screen_route) {
                                popUpTo(Screen.LoginScreen.route) { inclusive = true }
                            }
                            Toast.makeText(
                                context,
                                "Objetivo creado con exito!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Failure -> {
                        LaunchedEffect(goalValue.value) {
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    is Resource.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}