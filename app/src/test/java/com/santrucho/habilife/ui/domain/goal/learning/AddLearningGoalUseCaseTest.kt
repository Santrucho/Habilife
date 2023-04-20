package com.santrucho.habilife.ui.domain.goal.learning

import com.santrucho.habilife.ui.data.model.goals.LearningGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
import com.santrucho.habilife.ui.data.remote.goals.learning.LearningRepository
import com.santrucho.habilife.ui.data.remote.goals.training.TrainingGoalRepository
import com.santrucho.habilife.ui.domain.goal.training.AddTrainingGoalUseCase
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class AddLearningGoalUseCaseTest {

    @MockK
    private lateinit var repository: LearningRepository

    lateinit var addLearningGoalUseCase: AddLearningGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addLearningGoalUseCase = AddLearningGoalUseCase(repository)
    }

    @Test
    fun `when add learning goal is successfully`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val timesAWeek = 3
        val goal = LearningGoal("1","asd","Eeee","descripcion",false,"2023 04 20","eee","Learning",2)
        coEvery { repository.addGoal(any(),any(),any(),any(),any()) } returns Resource.Success(goal)
        //When
        val result = addLearningGoalUseCase(title, description, isCompleted, release_date,timesAWeek)
        //Then
        coVerify(exactly = 1) { repository.addGoal(title, description, isCompleted, release_date, timesAWeek) }
        Assert.assertEquals(Resource.Success(goal),result)
    }

    @Test
    fun `when add learning goal is failure`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val timesAWeek = 3
        val exception = RuntimeException("Error adding training goal")
        coEvery { repository.addGoal(any(),any(),any(),any(),any()) } returns Resource.Failure(exception)
        //When
        val result = addLearningGoalUseCase(title, description, isCompleted, release_date, timesAWeek)
        //Then
        coVerify(exactly = 1) { repository.addGoal(title, description, isCompleted, release_date, timesAWeek) }
        Assert.assertEquals(Resource.Failure(exception),result)
    }
}