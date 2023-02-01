package com.santrucho.habilife.ui.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.remote.goals.DefaultGoalsRepository
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.data.remote.habits.DefaultHabitsRepository
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.data.remote.signup.DefaultSignUpRepository
import com.santrucho.habilife.ui.data.remote.signup.SignUpRepository
import com.santrucho.habilife.ui.data.remote.login.DefaultLoginRepository
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.presentation.GoalViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideGoalViewModel(implementation: GoalsRepository) : GoalViewModel{
        return GoalViewModel(implementation)
    }

    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideSignUpRepository(implementation : DefaultSignUpRepository) : SignUpRepository = implementation

    @Provides
    fun provideLoginRepository(implementation: DefaultLoginRepository) : LoginRepository = implementation

    @Provides
    fun provideHabitsRepository(implementation: DefaultHabitsRepository) : HabitsRepository = implementation

    @Provides
    fun provideGoalsRepository(implementation: DefaultGoalsRepository) : GoalsRepository = implementation
}