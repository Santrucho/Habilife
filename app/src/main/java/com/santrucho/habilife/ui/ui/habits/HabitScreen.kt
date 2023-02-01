package com.santrucho.habilife.ui.ui.habits

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HabitScreen(habitViewModel:HabitViewModel,
                navController: NavController,
                isRefreshing: Boolean,
                refreshData: () -> Unit,
                habitsStateFlow: StateFlow<List<Habit>>
) {

    habitViewModel.resetResult()
    val state = habitViewModel.habitState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        RecommendedHabitsSection()
        Spacer(modifier = Modifier.height(32.dp))
        Text("MIS HABITOS", modifier = Modifier.padding(8.dp))
        //Set each element inside column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(8.dp)
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.Start
        ) {
            habitViewModel.getAllHabits()
            HabitList(habitsStateFlow,isRefreshing,refreshData,habitViewModel)
        }
        //Set FAB Button Row above BottomBar
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(Screen.NewHabitScreen.route){
                    popUpTo(BottomNavScreen.Habit.screen_route) {inclusive = true}
                } },
                modifier = Modifier.defaultMinSize(240.dp, 56.dp),
                shape = CircleShape

            ) {
                Text("Crear nuevo habito")
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}
//Set recommended habits section in screen
@Composable
fun RecommendedHabitsSection() {
    Text(text = "HABITOS RECOMENDADOS", modifier = Modifier.padding(8.dp), color = Black)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(1.dp, Black)){
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top

        ) {
            items(habitsData) { item ->
                RecommendedHabitsElement(item.drawable, item.text)
            }
        }
    }
}

//Set each element in recommended habits section
@Composable
fun RecommendedHabitsElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .width(72.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier
                .height(48.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_habits),
                contentDescription = null
            )
        }
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_name)
            )
        }
    }
}


//Set Fab Button which navigate to Create New Habit screen



private val habitsData = listOf(
    R.drawable.ic_habits to R.string.app_name,
    R.drawable.ic_habits to R.string.app_name,
    R.drawable.ic_habits to R.string.app_name,
    R.drawable.ic_habits to R.string.app_name,
    R.drawable.ic_habits to R.string.app_name,
    R.drawable.ic_habits to R.string.app_name,
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)



