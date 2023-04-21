package com.santrucho.habilife.ui.domain.goal.academic

import com.santrucho.habilife.ui.data.model.goals.AcademicGoal
import com.santrucho.habilife.ui.data.model.goals.TrainingGoal
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


internal class AddAcademicGoalUseCaseTest {

    @MockK
    private lateinit var academicRepository : AcademicGoalRepository

    lateinit var addAcademicGoalUseCase: AddAcademicGoalUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        addAcademicGoalUseCase = AddAcademicGoalUseCase(academicRepository)
    }

    @Test
    fun `when add academic goal is successfully`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val subject = "Matematica"
        val subjectList = listOf("Lengua, Matematica")
        val subjectApproved = listOf("Lengua")
        val goal = AcademicGoal("1","asd","Eeee","descripcion",false,"2023 04 20","eee","Academic",subject,subjectList,subjectApproved)
        coEvery { academicRepository.addGoal(any(),any(),any(),any(),any(),any(),any()) } returns Resource.Success(goal)
        //When
        val result = addAcademicGoalUseCase(title, description, isCompleted, release_date,subject, subjectList,subjectApproved)
        //Then
        coVerify(exactly = 1) { academicRepository.addGoal(title, description, isCompleted, release_date, subject, subjectList, subjectApproved) }
        Assert.assertEquals(Resource.Success(goal),result)
    }

    @Test
    fun `when add academic goal is failure`() = runBlocking {
        //Given
        val title = "Title"
        val description = "Descripcion"
        val isCompleted = false
        val release_date = "2023 04 20"
        val subject = "Matematica"
        val subjectList = listOf("Lengua, Matematica")
        val subjectApproved = listOf("Lengua")
        val exception = RuntimeException("Error adding training goal")
        coEvery { academicRepository.addGoal(any(),any(),any(),any(),any(),any(),any()) } returns Resource.Failure(exception)
        //When
        val result = addAcademicGoalUseCase(title, description, isCompleted, release_date, subject, subjectList, subjectApproved)
        //Then
        coVerify(exactly = 1) { academicRepository.addGoal(title, description, isCompleted, release_date,subject,subjectList,subjectApproved) }
        Assert.assertEquals(Resource.Failure(exception),result)
    }


}