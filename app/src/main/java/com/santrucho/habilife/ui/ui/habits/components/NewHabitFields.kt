package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.santrucho.habilife.ui.data.model.ItemList
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun NewHabitFields(habitViewModel: HabitViewModel) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {

        Text("Personalice su habito", fontSize = 20.sp, color = Color.Black)
        OutlinedTextField(
            value = habitViewModel.titleValue.value,
            onValueChange = {
                habitViewModel.titleValue.value = it
                habitViewModel.validateTitle()
            },
            isError = habitViewModel.isTitleValid.value,
            label = { Text(text = "Nombre del habito") },
            placeholder = { Text(text = "Habit title") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = CircleShape
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
            placeholder = { Text(text = "Description") },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = CircleShape
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = habitViewModel.descriptionErrMsg.value,
            fontSize = 14.sp,
            color = Color.Red
        )
        FrequentlyPicker()
        Spacer(modifier = Modifier.padding(8.dp))
        TimePicker()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories(options: Array<String>) {

    var selectedOption by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
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
                    label = { Text("Categories", color = Color.Black) },
                    placeholder = { Text("Seleccione un tipo de habito") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        textColor = Color.Black, focusedBorderColor = typeHelper(
                            habitType = selectedOption
                        ), focusedTrailingIconColor = typeHelper(habitType = selectedOption)
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth(1f)
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
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = selectionOption
                                expanded = false
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(4.dp)
                                .background(typeHelper(selectionOption), shape = CircleShape)
                        )
                        {
                            Text(
                                text = selectionOption,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimePicker() {

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
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
            title = "Elija un horario para comenzar el habito",
            colors = TimePickerDefaults.colors(
                MaterialTheme.colors.secondary,
                inactiveBackgroundColor = MaterialTheme.colors.background
            )
        ) { time ->
            pickedTime = time
            if (time.isAfter(LocalTime.NOON)) {
                timeSystem = "PM"
            } else {
                timeSystem = "AM"
            }
        }
    }
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.Start) {
            Text("Hora para realizar el habito", color = Color.Black, fontSize = 20.sp)
            Button(
                onClick = { dialogState.show() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.background
                ),
                shape = CircleShape,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            ) {
                Text(
                    "$formattedTime $timeSystem",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}