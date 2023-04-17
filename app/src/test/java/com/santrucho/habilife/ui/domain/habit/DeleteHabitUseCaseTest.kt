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


internal class DeleteHabitUseCaseTest{

    @MockK
    private lateinit var habitsRepository: HabitsRepository

    lateinit var deleteHabitUseCase: DeleteHabitUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        deleteHabitUseCase = DeleteHabitUseCase(habitsRepository)
    }

    @Test
    fun `when delete habit is successfully`() = runBlocking {
        //Given
        val habit = Habit("1", "asd2dfajkjd", "Title","Salud" ,listOf("Monday","Saturday"), "12:00", false,mutableListOf("18 04 2023"),false)
        coEvery { habitsRepository.deleteHabit(any()) } just Runs
        //When
        val result = deleteHabitUseCase(habit)
        //Then
        coVerify(exactly = 1) { habitsRepository.deleteHabit(habit) }
        assertEquals(Resource.Success(habit),result)
    }

    @Test
    fun `when delete habit is failure`() = runBlocking {
        //Given
        val habit = Habit("1", "asd2dfajkjd", "Title","Salud" ,listOf("Monday","Saturday"), "12:00", false,mutableListOf("18 04 2023"),false)
        val exception = RuntimeException("Error deleting habit")
        coEvery { habitsRepository.deleteHabit(any()) } throws exception
        //When
        val result = deleteHabitUseCase(habit)
        //Then
        coVerify(exactly = 1) { habitsRepository.deleteHabit(habit) }
        assertEquals(Resource.Failure(exception),result)
    }

}