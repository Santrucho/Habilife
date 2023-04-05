package com.santrucho.habilife.ui.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.GoalsUI
import com.santrucho.habilife.ui.ui.habits.components.HabitUI
import com.santrucho.habilife.ui.ui.home.components.EmptyMessage
import com.santrucho.habilife.ui.ui.home.components.HandleFilterState
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: SignUpViewModel,
    loginViewModel: LoginViewModel,
    goalViewModel: GoalViewModel,
    habitViewModel: HabitViewModel
) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Home Screen View","home_screen_view")


    goalViewModel.getAllGoals()
    habitViewModel.getAllHabits()

    LaunchedEffect(Unit) {
        userViewModel.resetValues()
    }

    val goal = goalViewModel.goalState.collectAsState()
    val habit = habitViewModel.habitState.collectAsState()

    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
    val filteredGoalList = goal.value.let { resource ->
        when (resource) {
            is Resource.Success -> resource.data.filter {
                LocalDate.parse(it.release_date, formatter) >= LocalDate.now()
            }.sortedBy {
                LocalDate.parse(it.release_date, formatter)
            }.take(1)
            else -> emptyList()
        }
    }

    val filteredHabitList = habit.value.let { resource ->
        when (resource) {
            is Resource.Success -> {
                resource.data.filter { habit ->
                    habit.frequently.any { day ->
                        day.equals(
                            LocalDate.now().dayOfWeek.getDisplayName(
                                TextStyle.FULL,
                                Locale("es", "ARG")
                            ), ignoreCase = true
                        )
                    }
                }
            }
            else -> {
                emptyList()
            }
        }
    }

    val onBack = {}
    BackPressHandler(onBackPressed = onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant),
    ) {

        //Welcomes the user to the main screen
        loginViewModel.currentUser?.let {
            UserWelcome(name = it.displayName.toString(), navController, loginViewModel::logout)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        //Show a list of habits to make in the current day
        TextInScreen(
            title = "Habitos del dia",
            route = BottomNavScreen.Habit.screen_route,
            navController = navController
        )
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .padding(8.dp, 2.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            //Set the box to show the Goals to make that day
            if (filteredHabitList.isEmpty()) {
                EmptyMessage("Al parecer no tienes ningun habito para realizar hoy","Crea mas habitos!")
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.37f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HandleFilterState(
                        flow = habit,
                        filteredList = filteredHabitList,
                        cardUI = HabitUI(
                            habits = filteredHabitList,
                            onDelete = habitViewModel::deleteHabit,
                            viewModel = habitViewModel
                        )
                    )
                }
            }
        }
        //Show the next goal to complete
        TextInScreen(
            title = "Proximo objetivo",
            route = BottomNavScreen.Goals.screen_route,
            navController = navController
        )
        if (filteredGoalList.isEmpty()) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .padding(8.dp, 2.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                EmptyMessage("Al parecer no tienes ningun objetivo para realizar proximamente","Crea nuevos objetivos y cumple tus metas!")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HandleFilterState(
                    flow = goal,
                    filteredList = filteredGoalList,
                    cardUI = GoalsUI(
                        goals = filteredGoalList,
                        navController = navController
                    )
                )
            }
        }
    }
}


/*Welcome to user, changing the background image depending of what time of day it is */
@Composable
fun UserWelcome(name: String, navController: NavController, onLogout: () -> Unit) {
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    var showOptions by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = "https://firebasestorage.googleapis.com/v0/b/habilife-2bba3.appspot.com/o/Habi(1).png?alt=media&token=8cbe8c2c-ee97-4f80-b8ba-e130fcce7379",
                contentDescription = "profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(68.dp)
                    .height(68.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "bienvenido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(
                    text = "$name",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp
                )
            }
            IconButton(onClick = {
                showOptions = !showOptions
                LogBundle.logBundleAnalytics(firebaseAnalytics,"Settings Pressed","settings_pressed")
                }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "settings",
                    modifier = Modifier
                        .width(58.dp),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
        Spacer(modifier = Modifier.padding(12.dp))

        if (showOptions) {
            AlertDialog(
                onDismissRequest = { showOptions = false },
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                title = {
                    Text(
                        text = "Ajustes",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                buttons = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            Divider(modifier = Modifier.padding(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Activar modo oscuro")
                                //MyChip(title = , selected = , onSelected = )
                            }
                            TextButton(
                                onClick = {
                                    onLogout()
                                    navController.navigate(Screen.LoginScreen.route)
                                    LogBundle.logBundleAnalytics(firebaseAnalytics,"SignOut Pressed","sign_out_pressed")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "Cerrar sesion",
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TextInScreen(title: String, route: String, navController: NavController) {
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
        TextButton(onClick = {
            navController.navigate(route)
            LogBundle.logBundleAnalytics(firebaseAnalytics,"See All","see_all_pressed")
        }) {
            Text(
                text = "ver todos",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.End,
                fontSize = 12.sp
            )
        }
    }
}


