package com.santrucho.habilife.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.GoalField

@Composable
fun CustomFieldDialog(goal: GoalsResponse, goalViewModel: GoalViewModel) {
    when (goal.type) {
        "Finance" -> {
            GoalField(text = "Monto actual: ", goalText = "${goal.amount.toString() ?: ""} $")

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                Text(
                    "Agregar monto:",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                TextField(
                    value = goalViewModel.amountValue.value?.toString() ?: "",
                    onValueChange = { goalViewModel.amountValue.value = it.toIntOrNull() },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Blue
                    )
                )
            }
        }
    }
}