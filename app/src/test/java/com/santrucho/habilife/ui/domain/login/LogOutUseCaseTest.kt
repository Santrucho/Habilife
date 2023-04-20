package com.santrucho.habilife.ui.domain.login

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
import java.lang.RuntimeException

internal class LogOutUseCaseTest {

    @MockK
    private lateinit var loginRepository: LoginRepository

    lateinit var logOutUseCase: LogOutUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        logOutUseCase = LogOutUseCase(loginRepository)
    }

    @Test
    fun `when log out is succesfully`() = runBlocking {
        //Given
        coEvery { loginRepository.logout() } returns Resource.Success(Unit)
        //When
        logOutUseCase()
        //Then
        coVerify(exactly = 1) { loginRepository.logout() }
    }

    @Test
    fun `when log out is failure`() = runBlocking {
        //Given
        val exception = RuntimeException("Error log out")
        coEvery { loginRepository.logout() } returns Resource.Failure(exception)
        //When
        val result = logOutUseCase()
        //Then
        coVerify(exactly = 1) { loginRepository.logout() }
        assertEquals(Resource.Failure(exception),result)
    }
}