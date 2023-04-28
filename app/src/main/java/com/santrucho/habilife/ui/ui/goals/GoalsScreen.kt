package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.FilterGoals
import com.santrucho.habilife.ui.ui.habits.components.MyChip
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.Resource

@Composable
fun GoalsScreen(goalViewModel: GoalViewModel, navController: NavController) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Goal Screen View","goal_screen_view")

    goalViewModel.resetResult()
    goalViewModel.getAllGoals()

    val onBack = { navController.navigate(BottomNavScreen.Home.screen_route) }
    BackPressHandler(onBackPressed = onBack)

    val goalState = goalViewModel.goalState.collectAsState()

    val goalCount = goalState.value.let { result ->
        when (result) {
            is Resource.Success -> result.data.size
            else -> 0
        }
    }

    var selectedChip by remember { mutableStateOf("Todos") }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant)) {
        //Set each element inside column
        Spacer(modifier = Modifier.padding(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(8.dp)
                .clickable { LogBundle.logBundleAnalytics(firebaseAnalytics,"Goal Count","goal_count_pressed") },
            shape = MaterialTheme.shapes.large,
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(
                    text = stringResource(id = R.string.goals),
                    color = Color.White,
                    modifier = Modifier.shadow(
                        elevation = 24.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight(900),
                )
                Text(
                    text = "$goalCount",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .shadow(
                            elevation = 16.dp,
                            ambientColor = Color.Black,
                        ),
                    color = MaterialTheme.colors.primary,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MyChip(
                title = stringResource(id = R.string.chips_all),
                selected = selectedChip == stringResource(id = R.string.chips_all),
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Todos"
                    } else {
                        ""
                    }
                },
                colorBackground = MaterialTheme.colors.background
            )
            MyChip(
                title = stringResource(id = R.string.chips_year),
                selected = selectedChip == stringResource(id = R.string.chips_year),
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Año"
                    } else {
                        ""
                    }
                },
                colorBackground = MaterialTheme.colors.background
            )
            MyChip(
                title = stringResource(id = R.string.chips_month),
                selected = selectedChip == stringResource(id = R.string.chips_month),
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Mes"
                    } else {
                        ""
                    }
                },
                colorBackground = MaterialTheme.colors.background
            )
        }

        FilterGoals(
            title = selectedChip,
            goalViewModel = goalViewModel,
            navController = navController
        )
    }
}
