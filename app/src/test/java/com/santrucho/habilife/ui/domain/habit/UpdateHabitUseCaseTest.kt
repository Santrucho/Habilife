package com.santrucho.habilife.ui.domain.habit

import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.RuntimeException

internal class UpdateHabitUseCaseTest{

    @MockK
    private lateinit var habitsRepository: HabitsRepository

    lateinit var updateHabitUseCase: UpdateHabitUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        updateHabitUseCase = UpdateHabitUseCase(habitsRepository)
    }

    @Test
    fun `when update habit is successfully`() = runBlocking {
        val habitId = "1"
        val isChecked = false
        val daysCompleted = mutableListOf("Monday","Saturday")
        val habit = Habit(habitId, "asd2dfajkjd", "Title","Salud" , daysCompleted, "12:00", isChecked,mutableListOf("18 04 2023"),false)
        //Given
        coEvery { habitsRepository.updateHabit(any(),any(),any()) } returns Resource.Success(Unit)

        //When
        val result = updateHabitUseCase(habitId,isChecked,daysCompleted)

        //Then
        coVerify(exactly = 1) { habitsRepository.updateHabit(habitId,isChecked,daysCompleted) }
        assertEquals(Resource.Success(Unit),result)
    }

    @Test
    fun `when update habit is failure`() = runBlocking {
        //Given
        val habitId = "1"
        val isChecked = false
        val daysCompleted = mutableListOf("Monday","Saturday")
        val exception = RuntimeException("Error updating habit")
        //Given
        coEvery { habitsRepository.updateHabit(any(),any(),any()) } returns Resource.Failure(exception)
        //When
        val result = updateHabitUseCase(habitId,isChecked,daysCompleted)
        //Then
        coVerify(exactly = 1) { habitsRepository.updateHabit(habitId,isChecked,daysCompleted) }
        assertEquals(Resource.Failure(exception),result)
    }
}