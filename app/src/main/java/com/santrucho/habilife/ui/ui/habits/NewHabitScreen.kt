package com.santrucho.habilife.ui.ui.habits

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.ui.habits.components.Categories
import com.santrucho.habilife.ui.ui.habits.components.FrequencyPicker
import com.santrucho.habilife.ui.ui.habits.components.TimePicker
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.HandleState
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.Resource
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewHabitScreen(habitViewModel: HabitViewModel, navController: NavController) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    val habitValue = habitViewModel.habitFlow.collectAsState()

    //Create the options to choose a type for any Habits
    val optionsState = habitViewModel.options.collectAsState()
    val options = optionsState.value?.let { result ->
        when(result){
            is Resource.Success -> {result.data}
            else -> {null}
        }
    }
    var selectedOption by remember { mutableStateOf(options?.get(0) ) }


    //Create the states and list of days calling viewmodel and firestore to display
    val daysState = habitViewModel.daysOfWeek.collectAsState()
    val daysList = daysState.value.let { result ->
        when(result){
            is Resource.Success -> {result.data}
            else -> {null}
        }
    }
    var selectedDays by remember { mutableStateOf(emptyList<String>()) }
    //Check if at least one day is selected to enabled the confirm button
    var areDaysSelected by remember { mutableStateOf(false) }
    var orderedDays = daysList?.filter { day ->
        selectedDays.contains(day) }?.sortedBy{ day -> daysList.indexOf(day)}


    //TimePicker
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
        }
    }

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(BottomNavScreen.Habit.screen_route) }

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Column(Modifier.fillMaxSize()) {

                //Set the fields to show and fill for create a new habit
                //Call Categories and NewHabitFields in NewHabitFields function


                Categories(options = options!!, onTypeSelection = { newOption ->
                        selectedOption = newOption
                })
                Spacer(modifier = Modifier.padding(4.dp))
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
                        Text("Personalice su habito", fontSize = 20.sp, color = Color.Black)
                        NewFields(text = "Habito",
                            value = habitViewModel.titleValue.value.toString() ?: "",
                            isError = habitViewModel.isTitleValid.value,
                            error = habitViewModel.titleErrMsg.value,
                            valueChange = {habitViewModel.titleValue.value = it },
                            onValidate = { habitViewModel.validateTitle() })

                        NewFields(text = "Descripcion",
                            value = habitViewModel.descriptionValue.value.toString() ?: "",
                            isError = habitViewModel.isDescriptionValid.value,
                            error = habitViewModel.descriptionErrMsg.value,
                            valueChange = { habitViewModel.descriptionValue.value = it },
                            onValidate = { habitViewModel.validateDescription() })
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                TimePicker(pickedTime, onTimePicked = { time ->
                    pickedTime = time
                })
                Spacer(modifier = Modifier.padding(4.dp))
                FrequencyPicker(daysList!!) { days ->
                    selectedDays = days
                    areDaysSelected = days.isNotEmpty()
                    if (orderedDays != null) {
                        orderedDays = selectedDays
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        //Makes the call in the ViewModel to access a database and create the habit
                        selectedOption?.let { selectedOption ->
                            habitViewModel.addHabit(
                                habitViewModel.titleValue.value,
                                habitViewModel.descriptionValue.value, selectedOption,
                                orderedDays!!, formattedTime, false
                            )
                            LogBundle.logBundleAnalytics(firebaseAnalytics,"Add Habit","add_habit_pressed")
                        }
                    },
                    enabled = areDaysSelected && habitViewModel.isEnabledConfirmButton.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(56.dp),
                    shape = CircleShape

                ) {
                    Text("Guardar habito",Modifier.padding(4.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                //In case the call is correct, navigate to Habit Screen and show the habit created, in case is Incorrect, show a error message
            }
            HandleState(flow = habitValue,
                navController = navController ,
                route = BottomNavScreen.Habit.screen_route ,
                text = "Habito creado",
                message = "Add Habit",
                eventName = "add_habit")
        }
    }
}

//Makes the app bar go to the last screen
@Composable
fun DetailsAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier.width(48.dp),
                    contentDescription = stringResource(id = R.string.app_name),
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    )
}