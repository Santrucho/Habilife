package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.habits.HabitCard
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HomeHabitList(habitViewModel: HabitViewModel) {



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
                TextButton(onClick = { /*TODO*/ }) {
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
            val habit = habitViewModel.habitState.collectAsState()

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
                            HabitOfTheDay(
                                habits = result.data,
                                habitViewModel::deleteHabit
                            )
                        }
                        is Resource.Failure -> {
                            LaunchedEffect(habit.value) {
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


@Composable
fun HabitOfTheDay(habits: List<Habit>, onDelete: (Habit) -> Unit) {
    val filteredList = habits.filter { it.frequently.contains("odos") }
    if(filteredList.isEmpty()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            EmptyHabits()
        }
    } else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(modifier=Modifier.padding(0.dp,0.dp,0.dp,8.dp)){
                items(filteredList) {
                    HabitCard(habit = it, onDelete = onDelete)
                }
            }
        }
    }
}

@Composable
fun EmptyHabits(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "No tiene ningun habito por cumplir hoy!" +
                    "Crea uno nuevo!",
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
    }
}