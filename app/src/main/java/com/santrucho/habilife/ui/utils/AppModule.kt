package com.santrucho.habilife.ui.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.santrucho.habilife.ui.data.remote.goals.DefaultGoalsRepository
import com.santrucho.habilife.ui.data.remote.goals.GoalsRepository
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.academic.DefaultAcademicGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.finance.DefaultFinanceGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.training.DefaultTrainingGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.work.DefaultWorkGoalRepository
import com.santrucho.habilife.ui.data.remote.goals.work.WorkGoalRepository
import com.santrucho.habilife.ui.data.remote.habits.DefaultHabitsRepository
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.data.remote.signup.DefaultSignUpRepository
import com.santrucho.habilife.ui.data.remote.signup.SignUpRepository
import com.santrucho.habilife.ui.data.remote.login.DefaultLoginRepository
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideGoalViewModel(implementation: GoalsRepository,finance:FinanceGoalRepository,academic:AcademicGoalRepository,work:WorkGoalRepository,training:TrainingGoalRepository) : GoalViewModel{
        return GoalViewModel(implementation,academic,finance,work,training)
    }

    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFireStorage() : FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun provideSignUpRepository(implementation : DefaultSignUpRepository) : SignUpRepository = implementation

    @Provides
    fun provideLoginRepository(implementation: DefaultLoginRepository) : LoginRepository = implementation

    @Provides
    fun provideHabitsRepository(implementation: DefaultHabitsRepository) : HabitsRepository = implementation

    @Provides
    fun provideGoalsRepository(implementation: DefaultGoalsRepository) : GoalsRepository = implementation

    @Provides
    fun provideFinanceGoalRepository(implementation:DefaultFinanceGoalRepository) : FinanceGoalRepository = implementation

    @Provides
    fun provideAcademicGoalRepository(implementation:DefaultAcademicGoalRepository) : AcademicGoalRepository = implementation

    @Provides
    fun provideWorkGoalRepository(implementation:DefaultWorkGoalRepository) : WorkGoalRepository = implementation

    @Provides
    fun provideTrainingGoalRepository(implementation:DefaultTrainingGoalRepository) : TrainingGoalRepository = implementation
}