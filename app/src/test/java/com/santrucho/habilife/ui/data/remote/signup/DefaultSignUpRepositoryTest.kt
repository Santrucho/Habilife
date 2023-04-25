package com.santrucho.habilife.ui.data.remote.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.util.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Before
import org.junit.Test

internal class DefaultSignUpRepositoryTest {

    @MockK
    private lateinit var firebaseAuth: FirebaseAuth

    @MockK
    private lateinit var firebaseFirestore: FirebaseFirestore

    lateinit var repository: DefaultSignUpRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        repository = DefaultSignUpRepository(firebaseAuth, firebaseFirestore)
    }

    /*
    @Test

    fun `when user is created correctly`() = runBlocking {
        //Given
        val username = "Santucho"
        val email = "ejemplo@gmail.com"
        val password = "Password1"
        val user = User("asd123", "santucho", "ejemplo@gmail.com", 0, 0, "123")

        coEvery { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns mockk {
            coEvery { await() } returns mockk {
                every { user } returns mockk {
                    every { userId } returns user.userId
                    every { email } returns user.email
                    every { username } returns user.username
                }
            }
        }

        coEvery { FirebaseMessaging.getInstance().token.await() } returns user.token

        //When
        val result = withTimeout(5000) {
            repository.createUser(username, email, password)
        }
        //Then
        coVerify(exactly = 1) { repository.createUser(username, email, password) }
        assertEquals(Resource.Success(user), result)
    }*/
}