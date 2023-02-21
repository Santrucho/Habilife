package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.utils.typeHelper
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//Set the fields to show and fill out
@Composable
fun NewHabitFields(habitViewModel: HabitViewModel) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
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

                Text("Personalice su habito:", fontSize = 20.sp, color = Color.Black)
                OutlinedTextField(
                    value = habitViewModel.titleValue.value,
                    onValueChange = {
                        habitViewModel.titleValue.value = it
                        habitViewModel.validateTitle()
                    },
                    isError = habitViewModel.isTitleValid.value,
                    label = { Text(text = "Nombre del habito") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = CircleShape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Blue
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = habitViewModel.titleErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )
                OutlinedTextField(
                    value = habitViewModel.descriptionValue.value,
                    onValueChange = {
                        habitViewModel.descriptionValue.value = it
                        habitViewModel.validateDescription()
                    },
                    isError = habitViewModel.isDescriptionValid.value,
                    label = { Text(text = "Descripcion") },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = CircleShape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Blue
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = habitViewModel.descriptionErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

//Show an Spinner or ExposedDropMenu to deploy a list with all the options for choose a type for each habit
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories(options: List<String>,onTypeSelection:(String) -> Unit) {

    var selectedOption by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }


    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Text("Seleccione un tipo de habito:", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.padding(2.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedOption,
                    onValueChange = { },
                    placeholder = { Text("Seleccione un tipo de habito") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        backgroundColor = typeHelper(
                            habitType = selectedOption
                        ),
                        focusedTrailingIconColor = Color.White, trailingIconColor = Color.White,
                        focusedBorderColor = typeHelper(
                            habitType = selectedOption
                        ),
                        unfocusedBorderColor = typeHelper(
                            habitType = selectedOption
                        ),
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .align(Alignment.CenterHorizontally)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight()
                ) {
                    options.forEach { option ->
                        val isSelected = option == selectedOption
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = option
                                onTypeSelection(selectedOption)
                                expanded = false
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(4.dp)
                                .background(typeHelper(option), shape = CircleShape)
                        )
                        {
                            Text(
                                text = option,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

//Set an action button to select a Time to make the habit created
@Composable
fun TimePicker(pickTime:LocalTime,onTimePicked:(LocalTime)->Unit) {

    var pickedTime by remember {
        mutableStateOf(pickTime)
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
        }
    }
    var timeSystem by remember {
        mutableStateOf("")
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
        timepicker(
            initialTime = LocalTime.now(),
            is24HourClock = true,
            title = "Elija un horario para comenzar el habito:",
            colors = TimePickerDefaults.colors(
                activeTextColor = Color.Black,
                inactiveTextColor = Color.Black,
                inactivePeriodBackground = Color.Black,
                headerTextColor = Color.Black ,
                activeBackgroundColor = Color.White,
                selectorColor = Color.Blue,
                selectorTextColor = Color.White,
                inactiveBackgroundColor = Color.White
            ),
        ) { time ->
            pickedTime = time
            if (time.isAfter(LocalTime.NOON)) {
                timeSystem = "PM"
            } else {
                timeSystem = "AM"
            }
            onTimePicked(time)
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
            Text("Hora para realizar el habito:", color = Color.Black, fontSize = 20.sp)
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
                    "$formattedTime $timeSystem",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}