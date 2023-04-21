package com.santrucho.habilife.ui.domain.goal.training

import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class AddTrainingGoalUseCaseTest {

    @MockK
    private lateinit var repository: TrainingGoalRepository

    lateinit var addTrainingGoalUseCase: AddTrainingGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addTrainingGoalUseCase = AddTrainingGoalUseCase(repository)
    }

    @Test
    fun `when add training goal is successfully`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val kilometers = 20
        val kilometersGoal = 250
        val goal = TrainingGoal("1","asd","Eeee","descripcion",false,"2023 04 20","eee","Training",2,56)
        coEvery { repository.addGoal(any(),any(),any(),any(),any(),any()) } returns Resource.Success(goal)
        //When
        val result = addTrainingGoalUseCase(title, description, isCompleted, release_date, kilometers, kilometersGoal)
        //Then
        coVerify(exactly = 1) { repository.addGoal(title, description, isCompleted, release_date, kilometers, kilometersGoal) }
        assertEquals(Resource.Success(goal),result)
    }

    @Test
    fun `when add training goal is failure`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val kilometers = 20
        val kilometersGoal = 250
        val exception = RuntimeException("Error adding training goal")
        coEvery { repository.addGoal(any(),any(),any(),any(),any(),any()) } returns Resource.Failure(exception)
        //When
        val result = addTrainingGoalUseCase(title, description, isCompleted, release_date, kilometers, kilometersGoal)
        //Then
        coVerify(exactly = 1) { repository.addGoal(title, description, isCompleted, release_date, kilometers, kilometersGoal) }
        assertEquals(Resource.Failure(exception),result)
    }
}