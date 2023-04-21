package com.santrucho.habilife.ui.domain.goal.finance

import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class UpdateFinanceGoalUseCaseTest{

    @MockK
    private lateinit var financeGoalRepository: FinanceGoalRepository

    lateinit var updateFinanceGoalUseCase: UpdateFinanceGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        updateFinanceGoalUseCase = UpdateFinanceGoalUseCase(financeGoalRepository)
    }

    @Test
    fun `when update finance goal is successfully`() = runBlocking {
        //Given
        val goalId = "123"
        val amount = 1240
        coEvery { financeGoalRepository.updateGoal(any(),any()) } returns Resource.Success(Unit)
        //When
        val result = updateFinanceGoalUseCase(goalId, amount)
        //Then
        coVerify(exactly = 1) { financeGoalRepository.updateGoal(goalId,amount) }
        assertEquals(Resource.Success(Unit),result)
    }

    @Test
    fun `when update finance goal is failure`() = runBlocking {
        //Given
        val goalId = "123"
        val amount = 1240
        val exception = RuntimeException("Error updating finance goal")
        coEvery { financeGoalRepository.updateGoal(any(),any()) } returns Resource.Failure(exception)
        //When
        val result = updateFinanceGoalUseCase(goalId, amount)
        //Then
        coVerify(exactly = 1) { financeGoalRepository.updateGoal(goalId,amount) }
        assertEquals(Resource.Failure(exception),result)
    }
}