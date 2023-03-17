package com.santrucho.habilife.ui.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.GoalsUI
import com.santrucho.habilife.ui.ui.habits.components.HabitUI
import com.santrucho.habilife.ui.ui.home.components.HandleFilterState
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: SignUpViewModel,
    goalViewModel: GoalViewModel,
    habitViewModel: HabitViewModel
) {

    goalViewModel.getAllGoals()
    habitViewModel.getAllHabits()

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
            .padding(8.dp),
    ) {

        //Welcomes the user to the main screen
        userViewModel.currentUser?.let {
            UserInfo(name = it.displayName.toString())
        }
        //Show a list of habits to make in the current day
        TextInScreen(
            title = "Habitos del dia",
            route = BottomNavScreen.Habit.screen_route,
            navController = navController
        )
        //Set the box to show the Goals to make that day
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 3.dp,
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(8.dp, 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HandleFilterState(
                    flow = habit,
                    emptyText = "habito",
                    filteredList = filteredHabitList,
                    cardUI = HabitUI(
                        habits = filteredHabitList,
                        onDelete = habitViewModel::deleteHabit,
                        viewModel = habitViewModel
                    )
                )
            }
        }
        //Show the next goal to complete
        TextInScreen(
            title = "Proximo objetivo",
            route = BottomNavScreen.Goals.screen_route,
            navController = navController
        )

        HandleFilterState(
            flow = goal,
            emptyText = "objetivo",
            filteredList = filteredGoalList,
            cardUI = GoalsUI(
                goals = filteredGoalList,
                navController = navController
            )
        )
    }
}

/*Welcome to user, changing the background image depending of what time of day it is */
@Composable
fun UserInfo(name: String) {
    Spacer(modifier = Modifier.height(16.dp))

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.20f)
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier.clickable { }
        ) {
            AsyncImage(
                model = "https://firebasestorage.googleapis.com/v0/b/habilife-2bba3.appspot.com/o/sunshinee.png?alt=media&token=7a57199f-0adb-4bd1-b104-6303ed8a42c7",
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.45f), BlendMode.Multiply)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = "BIENVENIDO",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 24.sp
                )
                Text(
                    text = "$name",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 60.sp
                )
            }
        }
    }
}

@Composable
fun TextInScreen(title: String, route: String, navController: NavController) {
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
        TextButton(onClick = { navController.navigate(route) }) {
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


