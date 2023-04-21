package com.santrucho.habilife.ui.domain.goal.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.remote.goals.academic.AcademicGoalRepository
import com.santrucho.habilife.ui.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class UpdateAcademicGoalUseCaseTest {

    @MockK
    private lateinit var academicRepository : AcademicGoalRepository

    lateinit var updatedAcademicGoalUseCase: UpdateAcademicGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        updatedAcademicGoalUseCase = UpdateAcademicGoalUseCase(academicRepository)
    }

    @Test
    fun `when update academic goal is successfully`() = runBlocking {
        //Given
        val goalId = "2323"
        val subjectApproved = listOf("Lengua")
        coEvery { academicRepository.updateGoal(any(),any()) } returns Resource.Success(Unit)
        //When
        val result = updatedAcademicGoalUseCase(goalId,subjectApproved)
        //Then
        coVerify(exactly = 1) { academicRepository.updateGoal(goalId, subjectApproved) }
        Assert.assertEquals(Resource.Success(Unit),result)
    }

    @Test
    fun `when update academic goal is failure`() = runBlocking {
        //Given
        val goalId = "2323"
        val subjectApproved = listOf("Lengua")
        val exception = RuntimeException("Error adding training goal")
        coEvery { academicRepository.updateGoal(any(),any()) } returns Resource.Failure(exception)
        //When
        val result = updatedAcademicGoalUseCase(goalId,subjectApproved)
        //Then
        coVerify(exactly = 1) { academicRepository.updateGoal(goalId,subjectApproved) }
        Assert.assertEquals(Resource.Failure(exception),result)
    }

}