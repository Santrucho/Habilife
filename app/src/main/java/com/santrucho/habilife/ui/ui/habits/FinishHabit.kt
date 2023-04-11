package com.santrucho.habilife.ui.ui.habits

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel

@Composable
fun FinishHabit(
    habitTitle: String,
    habitViewModel: HabitViewModel,
    habit:Habit,
    onDelete:(Habit) -> Unit,
    onExtended:(Habit) -> Unit,
    onDialogDismiss: () -> Unit,

) {
    val context = LocalContext.current
    val openDialog = habitViewModel.finishHabit.value
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
                    text = "Haz completado el habito: $habitTitle",
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
                            onDelete(habit)
                        },
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(48.dp)
                    ) {
                        Text(text = "Eliminar")
                    }
                    Button(
                        onClick = {
                            onExtended(habit)
                            onDialogDismiss()
                            Toast.makeText(context,"Se ha extendido el habito a 32 dias",Toast.LENGTH_SHORT).show()
                        },
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(48.dp)
                    ) {
                        Text(text = "Extender")
                    }
                }
            }
        }
    )
}