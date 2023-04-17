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


internal class GetHabitsUseCaseTest{

    @MockK
    private lateinit var habitsRepository: HabitsRepository

    lateinit var getHabitsUseCase: GetHabitsUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getHabitsUseCase = GetHabitsUseCase(habitsRepository)
    }

    @Test
    fun `when get habits successfully`() = runBlocking{
        //Given
        val habitsList = listOf(
            Habit("1", "aaaa", "Title 1", "Salud", listOf("Monday"), "12:00", false, mutableListOf(), false),
            Habit("2", "bbbb", "Title 2", "Entrenamiento", listOf("Tuesday"), "13:00", false, mutableListOf(), false),
            Habit("3", "cccc", "Title 3", "Otros", listOf("Wednesday"), "14:00", false, mutableListOf(), false)
        )
        coEvery { habitsRepository.getHabits() } returns Resource.Success(habitsList)

        //When
        val result = getHabitsUseCase()

        //Then
        coVerify(exactly = 1) { habitsRepository.getHabits() }
        assertEquals(Resource.Success(habitsList),result)
    }

    @Test
    fun `when get habits failure`() = runBlocking{

        //Given
        val exception = RuntimeException("Error getting habits")
        coEvery { habitsRepository.getHabits() } returns Resource.Failure(exception)

        //When
        val result = getHabitsUseCase()

        //Then
        coVerify(exactly = 1) { habitsRepository.getHabits() }
        assertEquals(Resource.Failure(exception),result)
    }

}