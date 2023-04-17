package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class FinishHabitUseCaseTest{

    @MockK
    private lateinit var habitsRepository: HabitsRepository

    lateinit var finishHabitUseCase: FinishHabitUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        finishHabitUseCase = FinishHabitUseCase(habitsRepository)
    }

    @Test
    fun `when an habit is finished successfully`() = runBlocking {
        //Given
        coEvery { habitsRepository.finishHabit(any(),any(),any()) } just Runs
        //When
        val result = finishHabitUseCase("1",2,false)
        //Then
        coVerify(exactly = 1) { habitsRepository.finishHabit("1",2,false) }
        assertEquals(Resource.Success("Habit finished successfully"), result)
    }

    @Test
    fun `when finish habit is failure`() = runBlocking {
        //Given
        val exception = RuntimeException("Error adding habit")
        coEvery { habitsRepository.finishHabit(any(), any(), any()) } throws exception

        //When
        val result = finishHabitUseCase("1",2,false)

        //Then
        coVerify(exactly = 1) { habitsRepository.finishHabit("1",2,false) }
        assertEquals(Resource.Failure(exception),result)
    }
}