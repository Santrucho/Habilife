package com.santrucho.habilife.ui.domain.goal.training

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


internal class UpdateTrainingGoalUseCaseTest{

    @MockK
    private lateinit var trainingGoalRepository: TrainingGoalRepository

    lateinit var updateTrainingGoalUseCase: UpdateTrainingGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        updateTrainingGoalUseCase = UpdateTrainingGoalUseCase(trainingGoalRepository)
    }

    @Test
    fun `when training goal is successfully`() = runBlocking {
        //Given
        val goalId = "123"
        val kilometers = 245
        coEvery { trainingGoalRepository.updateGoal(any(),any()) } returns Resource.Success(Unit)
        //When
        val result = updateTrainingGoalUseCase(goalId,kilometers)
        //Then
        coVerify (exactly = 1){ trainingGoalRepository.updateGoal(goalId,kilometers) }
        assertEquals(Resource.Success(Unit),result)
    }

    @Test
    fun `when training goal is failure`() = runBlocking {
        //Given
        val goalId = "123"
        val kilometers = 245
        val exception = RuntimeException("Error updating training goal")
        coEvery { trainingGoalRepository.updateGoal(any(),any()) } returns Resource.Failure(exception)
        //When
        val result = updateTrainingGoalUseCase(goalId,kilometers)
        //Then
        coVerify (exactly = 1){ trainingGoalRepository.updateGoal(goalId,kilometers) }
        assertEquals(Resource.Failure(exception),result)
    }
}