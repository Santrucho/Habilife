package com.santrucho.habilife.ui.ui.goals

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.components.GoalList

@Composable
fun GoalsScreen(goalViewModel: GoalViewModel, navController: NavController) {

    goalViewModel.resetResult()
    //val localContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        //Set each element inside column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(8.dp)
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "MIS OBJETIVOS", modifier = Modifier.padding(8.dp), color = Color.Black)
            GoalList(goalViewModel)
        }
        //Set FAB Button Row above BottomBar
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center
        ) {
            FabButton(navController)
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}

//Set Fab Button which navigate to Create New Habit screen
@Composable
fun FabButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.NewGoalScreen.route) },
        modifier = Modifier.defaultMinSize(240.dp, 56.dp),
        shape = CircleShape

    ) {
        Text("Crear nuevo objetivo")
    }
}