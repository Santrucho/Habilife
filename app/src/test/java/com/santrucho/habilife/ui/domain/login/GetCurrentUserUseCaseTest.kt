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

internal class GetCurrentUserUseCaseTest{

    @MockK
    private lateinit var loginRepository: LoginRepository

    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getCurrentUserUseCase = GetCurrentUserUseCase(loginRepository)
    }

    @Test
    fun `when get current user is successfully`() = runBlocking {
        //Given
        val user = User("1","example","example@gmail.com",0,0,"asd1")
        coEvery { loginRepository.currentUser() } returns Resource.Success(user)
        //When
        val result = getCurrentUserUseCase()
        //Then
        coVerify(exactly = 1) { loginRepository.currentUser() }
        assertEquals(Resource.Success(user),result)
    }

    @Test
    fun `when get current user is failure`() = runBlocking {
        //Given
        val exception = RuntimeException("Error getting user")
        coEvery { loginRepository.currentUser() } returns Resource.Failure(exception)
        //When
        val result = getCurrentUserUseCase()
        //Then
        coVerify(exactly = 1) { loginRepository.currentUser() }
        assertEquals(Resource.Failure(exception),result)
    }
}