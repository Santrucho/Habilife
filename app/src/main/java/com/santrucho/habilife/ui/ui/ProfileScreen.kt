package com.santrucho.habilife.ui.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel

@Composable
fun ProfileScreen(navController: NavController,loginViewModel: LoginViewModel, signUpViewModel: SignUpViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            signUpViewModel.currentUser?.let {
                UserInfo(
                    viewModel = signUpViewModel,
                    navController = navController,
                    name = it.displayName.toString(),
                    email = it.email.toString()
                )
            }
            Button(
                onClick = {
                    loginViewModel.logout()
                    signUpViewModel.logout()
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(BottomNavScreen.Profile.screen_route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
            ) {
                Text(
                    text = "Cerrar sesion"
                )
            }
        }
    }
}

@Composable
fun UserInfo(
    viewModel: SignUpViewModel?,
    navController: NavController,
    name: String,
    email: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hola de nuevo"
        )

        Text(
            text = name,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "nombre: ",
                    modifier = Modifier.weight(0.3f),
                )

                Text(
                    text = name,
                    modifier = Modifier.weight(0.7f),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "email: ",
                    modifier = Modifier.weight(0.3f),
                )

                Text(
                    text = email,
                    modifier = Modifier.weight(0.7f),
                )
            }
        }
    }
}
