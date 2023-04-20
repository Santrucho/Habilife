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
import com.santrucho.habilife.ui.ui.habits.FinishHabit
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

/**
 * Composable who show and structure the Home Screen.
 * Here is where the user can interact with te app, navigate to other actions,
 * also some data will be displayed here like goals and habits
 * This screen have a bottom bar navigation to navigate an another screens
 * and have a top bar to welcome the user and a settings button action
 *
 * @param navController Used to navigate between screens
 * @param userViewModel Use SignUpViewModel to get the user information and dispalyed in the top app bar
 * @param loginViewModel Use LoginViewModel for in Settings, make the option to log out
 * @param goalViewModel Use GoalViewModel to display the next goal to be achieved
 * @param habitViewModel Use HabitViewModel to show the habits that the user needs to do on the current day
 *
 */
@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: SignUpViewModel,
    loginViewModel: LoginViewModel,
    goalViewModel: GoalViewModel,
    habitViewModel: HabitViewModel
) {

    /**
     * context variable is used to have access to the local context used for Firebase Analytics
     * firebaseAnalytics create a instance to Firebase Analytics for information about user engagement
     */
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    /**
     * Call the object logBundleAnalytics which is create for have access in the whole app and no create an instance and repeat
     * code everytime when is needed
     * This line is common in the whole app to have this data for engagement
     */

    LogBundle.logBundleAnalytics(firebaseAnalytics,"Home Screen View","home_screen_view")


    /**
     * This function create a coroutine in this context to update data after a change is produced
     * and call three methods
     *
     * @method resetValues() reset the values of fields in signup and login and
     * the resource state to null
     * @method getAllGoals() get all goals when a new goal is created or deleted
     * @method getAllHabits() get all habits when a new habits is created or deleted
     *
     */

    LaunchedEffect(Unit) {
        userViewModel.resetValues()
        goalViewModel.getAllGoals()
        habitViewModel.getAllHabits()
    }

    //This variable is used in HandleState to get a response in the callback to have all goals for the user
    val goal = goalViewModel.goalState.collectAsState()

    //This variable is used in HandleState to get a response in the callback to have all habits for the user
    val habit = habitViewModel.habitState.collectAsState()

    //Formatted a date to be compatible with the date used in the habits and goals model
    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")


    /**
     * Get all the goals of the user, and filter that's goals who their release_date is the next to be committed
     * In case that are two or more with the same release date, only take the first
     * Use formatter to compare the dates with the same patterns
     */
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

    /**
     * Get all the habits of the user, and filter that's habits where in their frequently have a day which coincide
     * with the current date.
     * Can have more than one habits at time
     * In case there is not habits today, return an empty list
     */
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

    //If the user click onBack button of their cellphones, close the app
    val onBack = {}
    BackPressHandler(onBackPressed = onBack)

    //Set the ui to put and display the data
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant),
    ) {

        //Welcome the user to the main screen calling a UserWelcome function
        val username by loginViewModel.currentUser.collectAsState()
        Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",username.toString())
        username.let{ result ->
            when(result){
                is Resource.Success -> {
                    UserWelcome(name = result.data.username , navController, loginViewModel::logout)
                }
                is Resource.Failure -> {
                    Log.d("Error obteniendo nombre de usuario",result.exception.message.toString())
                }
                else -> {
                    Unit
                }
            }
        }


        Spacer(modifier = Modifier.padding(8.dp))

        //Call TextInScreen function to show a title and text to navigate to show all habits
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
            //Set the box to show the habits to make that day, in case this is empty, show a EmptyMessage function
            if (filteredHabitList.isEmpty()) {
                EmptyMessage("Al parecer no tienes ningun habito para realizar hoy","Crea mas habitos!")
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.37f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    /**
                     * Call HandleFilterState where makes the logic to have the habits from database and filter this
                     * using filteredHabitList
                     */
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


        //Call TextInScreen function to show a title and text to navigate to show all goals
        TextInScreen(
            title = "Proximo objetivo",
            route = BottomNavScreen.Goals.screen_route,
            navController = navController
        )
        //Set the box to show the habits to make that day, in case this is empty, show a EmptyMessage function
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
                /**
                 * Call HandleFilterState where makes the logic to have the goals from database and filter this
                 * using filteredGoalList
                 */
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


/**
 * Set a top bar with an image logo, greet the user and set a an settings icon to sign out
 *
 * @param name Username for user message welcome
 * @param navController Used for navigation between screens, in this case navigate to login screen
 * when user sign out
 * @param onLogout This function call the viewmodel and make the logic for sign out of the app
 *
*/
@Composable
fun UserWelcome(name: String, navController: NavController, onLogout: () -> Unit) {
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    var showOptions by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)) {
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


