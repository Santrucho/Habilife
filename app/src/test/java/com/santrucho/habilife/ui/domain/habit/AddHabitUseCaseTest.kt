package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class AddHabitUseCaseTest{

    @MockK
    private lateinit var habitsRepository: HabitsRepository

    lateinit var addHabitUseCase: AddHabitUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addHabitUseCase = AddHabitUseCase(habitsRepository)
    }

    @Test
    fun `when adding an habit successfully`() = runBlocking {
        //Given
        val habit = Habit("1", "asd2dfajkjd", "Title","Salud" ,listOf("Monday","Saturday"), "12:00", false,mutableListOf("18 04 2023"),false)
        coEvery { habitsRepository.addHabit(any(), any(), any(), any(), any()) } returns Resource.Success(habit)

        //When
        val result = addHabitUseCase("Title", "Salud", listOf("Monday","Saturday"), "12:00", false)

        //Then
        coVerify(exactly = 1) { habitsRepository.addHabit("Title", "Salud", listOf("Monday","Saturday"), "12:00", false) }
        assertEquals(Resource.Success(habit), result)
    }

    @Test
    fun `when failure adding an habit()`() = runBlocking {
        // Given
        val exception = RuntimeException("Error adding habit")
        coEvery { habitsRepository.addHabit(any(), any(), any(), any(), any()) } throws exception

        // When
        val result = addHabitUseCase("Title", "Salud", listOf("Monday"), "12:00", false)

        // Then
        coVerify(exactly = 1) { habitsRepository.addHabit("Title", "Salud", listOf("Monday"), "12:00", false) }
        assertEquals(Resource.Failure(exception), result)
        }
}