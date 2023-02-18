package com.santrucho.habilife.ui.ui.home.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.ItemList
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.habits.components.HabitUI
import com.santrucho.habilife.ui.utils.Resource
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

@Composable
fun HomeHabitList(navController: NavController, habitViewModel: HabitViewModel) {

    val habit = habitViewModel.habitState.collectAsState()

    //Set the box to show the habit to make that day
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Habitos del dia",
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                TextButton(onClick = { navController.navigate(BottomNavScreen.Habit.screen_route) }) {
                    Text(
                        text = "Ver todos",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                }
            }
            //Make the logic to call a list of habits which coincide with the current day
            Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",LocalDate.now().dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es","AR")))
            habit.value.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is Resource.Success -> {
                        val filteredList = result.data.filter{ habit ->
                            habit.frequently.any{ day ->
                                day.equals(LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es","ARG")),ignoreCase = true)
                            }

                        }
                        if (filteredList.isEmpty()) {
                            //Call EmptyMessage from HomeGoalList to show it
                            EmptyMessage("habito")
                        }
                        else{
                            HabitUI(
                                filteredList,
                                habitViewModel::deleteHabit
                            )
                        }
                    }
                    is Resource.Failure -> {
                        LaunchedEffect(habit) {
                            result.exception.message.toString()
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