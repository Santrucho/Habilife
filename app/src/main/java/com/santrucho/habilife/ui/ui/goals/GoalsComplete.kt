package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.presentation.GoalViewModel

@Composable
fun GoalsComplete(
    goalTitle: String,
    goalViewModel: GoalViewModel,
    onDialogDismiss: () -> Unit
) {
    val openDialog = goalViewModel.isCompleted.value
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
                                onDialogDismiss()
                                //navController.navigate(BottomNavScreen.Goals.screen_route)
                                //LogBundle.logBundleAnalytics(firebaseAnalytics,"Cancel Goal Update","cancel_goal_update_pressed")\
                            },
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                        Button(
                            onClick = {
                                onDialogDismiss()
                                //navController.navigate(BottomNavScreen.Goals.screen_route)
                            },
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        )
}
