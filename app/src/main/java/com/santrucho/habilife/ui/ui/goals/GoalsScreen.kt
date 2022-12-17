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

@Composable
fun GoalsScreen(navController: NavController) {

    val localContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        RecommendedGoalsSection()
        Spacer(modifier = Modifier.height(32.dp))
        //Set each element inside column
        MyGoalsSection()
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
//Set recommended habits section in screen
@Composable
fun RecommendedGoalsSection() {
    Text(text = "OBJETIVOS RECOMENDADOS", modifier = Modifier.padding(8.dp), color = Color.Black)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(1.dp, Color.Black)){
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top

        ) {
            items(goalsData) { item ->
                RecommendedGoalsElement(item.drawable, item.text)
            }
        }
    }
}

//Set each element in recommended habits section
@Composable
fun RecommendedGoalsElement(
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

//Set my habits list section in screen
@Composable
fun MyGoalsSection(){
    Text("MIS OBJETIVOS", modifier = Modifier.padding(8.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .padding(8.dp)
            .border(0.4.dp, Color.Gray)
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.Start
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start

        ) {
            items(goalsData) { item ->
                GoalsElement(item.drawable, item.text)
            }
        }
    }
}

//Set each element in My Habits list section
@Composable
fun GoalsElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(106.dp)
            .padding(8.dp)
            .border(1.dp, Color.Gray),
    ) {
        Row(
            modifier
                .width(48.dp)
                .fillMaxHeight(),
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
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.app_name)
                )
                Text(
                    text = "Descripcion"
                )
                Text(
                    text = "Fecha limite"
                )
            }
        }
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
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
    }
}


private val goalsData = listOf(
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
