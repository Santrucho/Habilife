package com.santrucho.habilife.ui.ui.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.habits.components.calendar.CalendarView
import com.santrucho.habilife.ui.ui.habits.components.HabitList
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.LogBundle

@Composable
fun HabitScreen(
    habitViewModel: HabitViewModel,
    navController: NavController,
) {


    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Habit Screen View","habit_screen_view")

    val onBack = { navController.navigate(BottomNavScreen.Home.screen_route) }
    BackPressHandler(onBackPressed = onBack)

    habitViewModel.resetResult()
    //Set the screen in habits

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CalendarView(habitViewModel)
        Spacer(modifier = Modifier.padding(4.dp))
        MyHabitsSection(habitViewModel = habitViewModel)
    }
}

/*Set and display the currents habits create for the user
    Calling the list of habits in HabitList */
@Composable
fun MyHabitsSection(habitViewModel: HabitViewModel) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Mis habitos",
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.wrapContentWidth(Alignment.Start),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp)
    ) {
        HabitList(habitViewModel)
    }
}




