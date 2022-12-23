package com.santrucho.habilife.ui.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.habilife.ui.data.repository.signup.DefaultSignUpRepository
import com.santrucho.habilife.ui.data.repository.signup.SignUpRepository
import com.santrucho.habilife.ui.data.repository.login.DefaultLoginRepository
import com.santrucho.habilife.ui.data.repository.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideSignUpRepository(implementation : DefaultSignUpRepository) : SignUpRepository = implementation

    @Provides
    fun provideLoginRepository(implementation: DefaultLoginRepository) : LoginRepository = implementation
}