package com.santrucho.habilife.ui.ui.habits

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.Resource


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewHabitScreen(habitViewModel: HabitViewModel, navController: NavController,habit: Habit) {

    val habitValue = habitViewModel.habitFlow.collectAsState()
    val options = arrayOf("Food", "Sleep", "Drink")

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    val context = LocalContext.current

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(BottomNavScreen.Habit.screen_route) }

    val habitToSave = Habit(habit.id,habit.userId,habit.title,habit.description,habit.type,habit.frequently,habit.isCompleted,habit.isExpanded)

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                TextField(
                    value = habitViewModel.titleValue.value,
                    onValueChange = {
                        habitViewModel.titleValue.value = it
                        habitViewModel.validateTitle()
                    },
                    isError = habitViewModel.isTitleValid.value,
                    placeholder = { Text(text = "Habit title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = habitViewModel.titleErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )
                TextField(
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
                        .fillMaxHeight(0.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = habitViewModel.descriptionErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.padding(8.dp))

                TextField(
                    value = habitViewModel.frequencyValue.value,
                    onValueChange = {
                        habitViewModel.frequencyValue.value = it
                        habitViewModel.validateFrequently()
                    },
                    isError = habitViewModel.isFrequentlyValid.value,
                    label = { Text(text = "Frecuencia") },
                    placeholder = { Text(text = "frequency") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = habitViewModel.frequentlyMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded } ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { },
                        label = { Text("Categories") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = selectionOption
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            habitViewModel.addHabit(
                                habitToSave
                            )
                        },
                        enabled = habitViewModel.isEnabledConfirmButton.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .fillMaxHeight(0.2f),
                        shape = CircleShape

                    ) {
                        Text("Guardar habito")
                    }
                    habitValue.value.let {
                        when (it) {
                            is Resource.Success -> {
                                LaunchedEffect(Unit) {
                                    navController.navigate(BottomNavScreen.Habit.screen_route) {
                                        popUpTo(Screen.NewHabitScreen.route) { inclusive = true }
                                    }
                                    Toast.makeText(
                                        context,
                                        "Habito creado correctamente!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            is Resource.Failure -> {
                                LaunchedEffect(habitValue.value) {
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
                            else -> {
                                IllegalAccessException()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.app_name),
                )
            }
        }
    )
}