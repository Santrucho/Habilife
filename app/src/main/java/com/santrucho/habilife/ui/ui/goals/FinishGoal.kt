package com.santrucho.habilife.ui.ui.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.ReleaseDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FinishGoal(
    goalTitle: String,
    goalViewModel: GoalViewModel,
    goal : GoalsResponse,
    navController: NavController,
    onFinish: (GoalsResponse) -> Unit,
    onExtended : (GoalsResponse,String) -> Unit,
    onDialogDismiss: () -> Unit
) {

    val context = LocalContext.current
    var showExtendedDialog by remember { mutableStateOf(false)}
    val openDialog = goalViewModel.finishGoal.collectAsState().value
        AlertDialog(
            onDismissRequest = { !openDialog },
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            title = {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Felicidades!",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Haz completado el objetivo: $goalTitle",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            buttons = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                      showExtendedDialog = true
                            },
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Extender")
                        }
                        Button(
                            onClick = {
                                onFinish(goal)
                                navController.navigate(BottomNavScreen.Goals.screen_route)
                                //navController.navigate(BottomNavScreen.Goals.screen_route)
                            },
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Finalizar")
                        }
                    }
                }
                if(showExtendedDialog){
                    ExtendedGoalDate(goal = goal, onExtended = onExtended, onDialogDismiss = { showExtendedDialog = false }, navController = navController )
                }
            }
        )
}

@Composable
fun ExtendedGoalDate(goal:GoalsResponse,onExtended:(GoalsResponse,String) -> Unit,onDialogDismiss: () -> Unit,navController:NavController){

    var openDialog by remember { mutableStateOf(false)}
    //Pick date
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MM yyyy").format(pickedDate)
        }
    }

    ReleaseDatePicker(pickDate = pickedDate, onDatePicked = { date ->
        pickedDate = date
    })

    AlertDialog(
        onDismissRequest = { !openDialog },
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        title = {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Seleccione una nueva fecha!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            ReleaseDatePicker(pickDate = pickedDate, onDatePicked = { date ->
                pickedDate = date
            })
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            onDialogDismiss()
                        },
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(48.dp)
                    ) {
                        Text(text = "Cancelar")
                    }
                    Button(
                        onClick = {
                            onExtended(goal,formattedDate)
                            navController.navigate(BottomNavScreen.Goals.screen_route)
                        },
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(48.dp)
                    ) {
                        Text(text = "Confirmar")
                    }
                }
            }
        }
    )
}
