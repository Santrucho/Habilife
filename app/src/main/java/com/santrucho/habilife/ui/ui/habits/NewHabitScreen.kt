package com.santrucho.habilife.ui.ui.habits

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun NewHabitScreen(habitViewModel: HabitViewModel,navController: NavController) {

    var titleValue by remember { mutableStateOf("") }
    var descriptionValue by remember { mutableStateOf("") }
    var frequencyValue by remember { mutableStateOf("") }

    val habitFlow = habitViewModel.habitFlow.collectAsState()
    val context = LocalContext.current

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(BottomNavScreen.Habit.screen_route) }

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
                    placeholder = { Text(text = "Habit title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                TextField(
                    value = descriptionValue,
                    onValueChange = { descriptionValue = it },
                    label = { Text(text = "Descripcion") },
                    placeholder = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                TextField(
                    value = frequencyValue,
                    onValueChange = { frequencyValue = it },
                    label = { Text(text = "Frecuencia") },
                    placeholder = { Text(text = "frequency") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.padding(16.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = {habitViewModel.addHabit("",titleValue,descriptionValue,"",frequencyValue,false,false)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .fillMaxHeight(0.2f),
                        shape = CircleShape

                    ) {
                        Text("Guardar habito")
                    }
                }

            }
        }
    }

    habitFlow.value?.let{
        when(it){
            is Resource.Success ->{
                LaunchedEffect(Unit){
                    navController.navigate(BottomNavScreen.Habit.screen_route){
                        //popUpTo(Screen.LoginScreen.route) {inclusive = true}
                    }
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(habitFlow.value){
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