package com.santrucho.habilife.ui.ui.goals.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ReleaseDatePicker(pickDate: LocalDate, onDatePicked: (LocalDate) -> Unit) {

    var pickedDate by remember {
        mutableStateOf(pickDate)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MM yyyy").format(pickedDate)
        }
    }

    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancel")
        },
        properties = DialogProperties()
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Elija una fecha limite para el objetivo",
            colors = DatePickerDefaults.colors(
                dateActiveTextColor = MaterialTheme.colors.background,
                dateInactiveTextColor = MaterialTheme.colors.background,
                dateInactiveBackgroundColor = MaterialTheme.colors.secondary,
                headerTextColor = MaterialTheme.colors.background,
                headerBackgroundColor = MaterialTheme.colors.secondary,
                dateActiveBackgroundColor = MaterialTheme.colors.primary,
                calendarHeaderTextColor = MaterialTheme.colors.primaryVariant
            ),
            locale = Locale("ES","es")
        ) { date ->
            pickedDate = date
            onDatePicked(date)
        }
    }
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Text("Fecha para realizar el objetivo:", fontSize = 20.sp)

            Button(
                onClick = { dialogState.show() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        modifier = Modifier.width(28.dp),
                        contentDescription = "arrow forward",
                        tint = MaterialTheme.colors.primary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        formattedDate,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}