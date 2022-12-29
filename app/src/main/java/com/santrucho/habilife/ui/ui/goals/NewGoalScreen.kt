package com.santrucho.habilife.ui.ui.goals

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun NewGoalScreen(goalViewModel: GoalViewModel, navController: NavController) {

    var titleValue by remember { mutableStateOf("") }
    var descriptionValue by remember { mutableStateOf("") }
    var release_date by remember { mutableStateOf("") }

    val goalFlow = goalViewModel.goalFlow.collectAsState()
    val context = LocalContext.current

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(BottomNavScreen.Goals.screen_route) }

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                TextField(
                    value = titleValue,
                    onValueChange = { titleValue = it },
                    placeholder = { Text(text = "Nombre del objetivo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                TextField(
                    value = descriptionValue,
                    onValueChange = { descriptionValue = it },
                    label = { Text(text = "Descripcion") },
                    placeholder = { Text(text = "Description del objetivo") },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                TextField(
                    value = release_date,
                    onValueChange = { release_date = it },
                    label = { Text(text = "Fecha limite") },
                    placeholder = { Text(text = "Fecha limite para cumplir el objetivo") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.padding(16.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = {goalViewModel.addGoal(titleValue,descriptionValue,false,release_date)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .fillMaxHeight(0.2f),
                        shape = CircleShape

                    ) {
                        Text("Guardar objetivo")
                    }
                }

            }
        }
    }

    goalFlow.value?.let{
        when(it){
            is Resource.Success ->{
                LaunchedEffect(Unit){
                    navController.navigate(BottomNavScreen.Goals.screen_route){
                        popUpTo(Screen.LoginScreen.route) {inclusive = true}
                    }
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(goalFlow.value){
                    Toast.makeText(context,it.exception.message, Toast.LENGTH_LONG).show()
                }
            }
            is Resource.Loading -> {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }

            }
        }
    }

}

@Composable
fun DetailsAppBar(onBack: () -> Unit){
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.app_name),
                )
            }
        }
    )
}