package com.santrucho.habilife.ui.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.ui.signup.SignUpViewModel

@Composable
fun ProfileScreen(viewModel: SignUpViewModel?,navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        viewModel?.currentUser?.let {
            UserInfo(
                viewModel = viewModel,
                navController = navController,
                name = it.displayName.toString(),
                email = it.email.toString()
            )
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
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
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