package com.santrucho.habilife.ui.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.TextFields
import com.santrucho.habilife.ui.ui.goals.components.PasswordFields
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.HandleState
import com.santrucho.habilife.ui.util.LogBundle

/**
 * Composable who show and structure the Sign up Screen. Here the user can
 * fill the fields with her information and create a new account for the app.
 * This information is saved into the database for have own data and content
 * The user can navigate of Login to here, and of here to login
 *
 * @param viewModel Use SignUpViewModel to interact with information and have access to data in the database
 * @param navController Used to navigate between screens
 *
 */
@Composable
fun SignUpScreen(viewModel: SignUpViewModel,navController: NavController) {

    /**
     * context variable is used to have access to the local context used for Firebase Analytics
     * firebaseAnalytics create a instance to Firebase Analytics for information about user engagement
     */
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    /**
     * Call the object logBundleAnalytics which is create for have access in the whole app and no create an instance and repeat
     * code everytime when is needed
     * This line is common in the whole app to have this data for engagement
     */
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Signup Screen View","sign_up_screen_view")

    //If the user click onBack button of their cellphones, go to the login screen
    val onBack = {navController.navigate(Screen.LoginScreen.route)}
    BackPressHandler(onBackPressed = onBack)

    /*Collect in async way values and create an account for the user.
      * This variable is called in HandleState who is in charge to makes this logic
      * and communicate with the backend
     */
    val signUpFlow = viewModel.signUpFlow.collectAsState()

    //Set the fields and button in Sign Up to fill by the user
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.padding(12.dp))

                    /*
                       * This functions create a fields who interact with view model
                       * for correct validations and forms.
                       * Is used in some screens to no repeat code
                       *
                       * This is the field to Username data
                     */

                    TextFields(text = "Username",
                        value = viewModel.usernameValue.value,
                        isError = viewModel.isUsernameValid.value,
                        error = viewModel.usernameErrMsg.value,
                        valueChange = { viewModel.usernameValue.value = it }, onValidate = { viewModel.validateUsername() })

                    /**
                     * This is the field for email data
                     */

                    TextFields(text = "Email",
                        value = viewModel.emailValue.value,
                        isError = viewModel.isEmailValid.value,
                        error = viewModel.emailErrMsg.value,
                        valueChange = { viewModel.emailValue.value = it }, onValidate = { viewModel.validateEmail() })

                    /*
                       * This function create a field who interact with view model
                       * for correct validations and forms to the password.
                       * Is used in some screens to no repeat code and have personalized fields
                       *
                       * This is the field for password data
                     */

                    PasswordFields(
                        text = "Password",
                        value = viewModel.passwordValue,
                        isError = viewModel.isPasswordValid,
                        error = viewModel.passwordErrMsg,
                        passwordVisibility = viewModel.passwordVisibility,
                        valueChange = { it },
                        onValidate = { viewModel.validatePassword() }
                    )

                    /**
                     * Here viewModel.confirmPassword() make the logic for validations and
                     * forms for confirm the password
                     */

                    PasswordFields(
                        text = "Confirm password",
                        value = viewModel.confirmPasswordValue,
                        isError = viewModel.isConfirmPasswordValid,
                        error = viewModel.confirmPasswordErrMsg,
                        passwordVisibility = viewModel.confirmPasswordVisibility,
                        valueChange = { it },
                        onValidate = { viewModel.confirmPassword() }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    //In case the call is correct, call ViewModel to add user in the database, and navigate to HomeScreen
                    Button(
                        //When the button is pressed, call HandleState and in case to all works, create an account and navigate the user to Home Screen
                        onClick = {
                            /*
                             * This function call sign up in View Model who makes the logic to create a nwe account and save it to the database
                             * for have access and personalized information
                             */
                            viewModel.signUp(
                                viewModel.usernameValue.value,
                                viewModel.emailValue.value,
                                viewModel.passwordValue.value
                            )

                            LogBundle.logBundleAnalytics(firebaseAnalytics,"Register Pressed","register_pressed")
                        },
                        shape = CircleShape,
                        enabled = viewModel.isEnabledConfirmButton.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = "Create account"
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    /**
                     * Set the option to navigate to Sign Up Screen,
                     * where the user can create a new account and register in the app
                     */

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate(Screen.LoginScreen.route)
                            LogBundle.logBundleAnalytics(firebaseAnalytics,"Go to Login","go_to_login_pressed")
                        }),
                        horizontalArrangement = Arrangement.Center){

                        Text(
                            text = "You already have an account? ",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Sign in",
                            modifier = Modifier,
                            color = Color.Blue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }

            //Make the logic to the database call to add a new user, evaluating three possible cases:
            //Success in case the call is correct, Failure in case the call is incorrect and Loading
            HandleState(
                flow = signUpFlow,
                navController = navController,
                route = BottomNavScreen.Home.screen_route,
                text = "Cuenta creada",
                message = "Sign up",
                eventName = "sign_up"
            )
        }
    }
}
