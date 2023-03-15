package com.santrucho.habilife.ui.ui.goals.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ReleaseDatePicker(pickDate:LocalDate,onDatePicked:(LocalDate)->Unit){

    var pickedDate by remember {
        mutableStateOf(pickDate)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd mm yyyy").format(pickedDate)
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
    ){
        datepicker(
            initialDate = LocalDate.now(),
            title = "Elija una fecha limite para el objetivo",
            colors = DatePickerDefaults.colors(
            dateActiveTextColor = Color.Black,
            dateInactiveTextColor = Color.Black,
            dateInactiveBackgroundColor = Color.Black,
            headerTextColor = Color.Black ,
            headerBackgroundColor = Color.White,
            dateActiveBackgroundColor = Color.White,
            calendarHeaderTextColor = Color.Blue
        )){ date ->
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
                    backgroundColor = Color.Blue,
                    contentColor = MaterialTheme.colors.background
                ),
                shape = CircleShape,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            ) {
                Text(
                    formattedDate,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}