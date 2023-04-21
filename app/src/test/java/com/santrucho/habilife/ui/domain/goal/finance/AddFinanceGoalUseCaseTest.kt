package com.santrucho.habilife.ui.domain.goal.finance

import com.santrucho.habilife.ui.data.model.goals.FinanceGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.data.remote.goals.finance.FinanceGoalRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class AddFinanceGoalUseCaseTest {

    @MockK
    private lateinit var financeGoalRepository: FinanceGoalRepository

    lateinit var addFinanceGoalUseCase: AddFinanceGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addFinanceGoalUseCase = AddFinanceGoalUseCase(financeGoalRepository)
    }

    @Test
    fun `when add finance goal is successfully`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val amount = 20
        val amountGoal = 250
        val goal = FinanceGoal("1","asd","Eeee","descripcion",false,"2023 04 20","eee","Finance",2,56)
        coEvery { financeGoalRepository.addGoal(any(),any(),any(),any(),any(),any()) } returns Resource.Success(goal)
        //When
        val result = addFinanceGoalUseCase(title, description, isCompleted, release_date, amount, amountGoal)
        //Then
        coVerify(exactly = 1) { financeGoalRepository.addGoal(title, description, isCompleted, release_date, amount, amountGoal) }
        assertEquals(Resource.Success(goal), result)
    }

    @Test
    fun `when add finance goal is failure`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val kilometers = 20
        val kilometersGoal = 250
        val exception = RuntimeException("Error adding training goal")
        coEvery { financeGoalRepository.addGoal(any(),any(),any(),any(),any(),any()) } returns Resource.Failure(exception)
        //When
        val result = addFinanceGoalUseCase(title, description, isCompleted, release_date, kilometers, kilometersGoal)
        //Then
        coVerify(exactly = 1) { financeGoalRepository.addGoal(title, description, isCompleted, release_date, kilometers, kilometersGoal) }
        Assert.assertEquals(Resource.Failure(exception), result)
    }
}