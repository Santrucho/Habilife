package com.santrucho.habilife.ui.domain.signup

import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.signup.SignUpRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class AddUserUseCaseTest {

    @MockK
    private lateinit var signUpRepository: SignUpRepository

    lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addUserUseCase = AddUserUseCase(signUpRepository)
    }

    @Test
    fun `when create user is successfully`() = runBlocking {
        //Given
        val username = "Hecotr"
        val email = "email@gmail.com"
        val password = "asd"
        val user = User("1","hector","email@gmail.com",3,2,"asdjf")
        coEvery { signUpRepository.createUser(any(),any(),any())} returns Resource.Success(user)
        //When
        val result = addUserUseCase(username,email,password)
        //Then
        coVerify(exactly = 1) { signUpRepository.createUser(username,email,password) }
        assertEquals(Resource.Success(user),result)
    }

    @Test
    fun `when create user is failure`() = runBlocking {
        val username = "Hecotr"
        val email = "email@gmail.com"
        val password = "asd"
        val exception = RuntimeException("Error adding user")
        coEvery { signUpRepository.createUser(any(),any(),any())} returns Resource.Failure(exception)
        //When
        val result = addUserUseCase(username,email,password)
        //Then
        coVerify(exactly = 1) { signUpRepository.createUser(username,email,password) }
        assertEquals(Resource.Failure(exception),result)
    }

}