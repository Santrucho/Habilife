package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.santrucho.habilife.ui.util.typeHelper
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//Show an Spinner or ExposedDropMenu to deploy a list with all the options for choose a type for each habit
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories(options: List<String>, onTypeSelection: (String) -> Unit) {

    var selectedOption by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }


    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp,
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
                            expanded = !expanded
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
fun TimePicker(pickTime: LocalTime, onTimePicked: (LocalTime) -> Unit) {

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
            is24HourClock = false,
            title = "Elija un horario para comenzar el habito:",
            colors = TimePickerDefaults.colors(
                activeTextColor = MaterialTheme.colors.background,
                inactiveTextColor = MaterialTheme.colors.background,
                inactivePeriodBackground = MaterialTheme.colors.secondary,
                headerTextColor = MaterialTheme.colors.primaryVariant,
                activeBackgroundColor = MaterialTheme.colors.primary,
                inactiveBackgroundColor = MaterialTheme.colors.secondary,
                selectorColor = MaterialTheme.colors.primary,
                selectorTextColor = MaterialTheme.colors.primary,
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
            Text(
                "Hora para realizar el habito",
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 20.sp
            )
            Button(
                onClick = { dialogState.show() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = CircleShape,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        modifier = Modifier.width(28.dp),
                        contentDescription = "arrow forward",
                        tint = MaterialTheme.colors.primary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "$formattedTime $timeSystem",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}