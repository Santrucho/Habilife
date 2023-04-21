package com.santrucho.habilife.ui.domain.login

import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class LoginUseCaseTest{

    @MockK
    private lateinit var loginRepository: LoginRepository

    lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase(loginRepository)
    }

    @Test
    fun `when login is successfully`() = runBlocking {
        //Given
        val email = "email@gmail.com"
        val password = "Asdeas1"
        val user = User("1","Esaa","email@gmail.com",0,0,"1asd")
        coEvery { loginRepository.loginUser(any(),any()) } returns Resource.Success(user)
        //When
        val result = loginUseCase(email,password)
        //Then
        coVerify(exactly = 1) { loginRepository.loginUser(email,password) }
        assertEquals(Resource.Success(user),result)
    }

    @Test
    fun `when login is failure`() = runBlocking {
        //Given
        val email = "email@gmail.com"
        val password = "Asdeas1"
        val exception = RuntimeException("Error login")
        coEvery { loginRepository.loginUser(any(),any()) } returns Resource.Failure(exception)
        //When
        val result = loginUseCase(email,password)
        //Then
        coVerify(exactly = 1) { loginRepository.loginUser(email,password) }
        assertEquals(Resource.Failure(exception),result)
    }

}