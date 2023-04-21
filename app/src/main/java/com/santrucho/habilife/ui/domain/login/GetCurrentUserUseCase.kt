package com.santrucho.habilife.ui.domain.login

import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(): Resource<User> {
        return try {
            repository.currentUser()
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}