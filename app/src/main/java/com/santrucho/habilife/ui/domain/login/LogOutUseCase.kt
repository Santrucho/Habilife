package com.santrucho.habilife.ui.domain.login

import android.util.Log
import com.santrucho.habilife.ui.data.model.User
import com.santrucho.habilife.ui.data.remote.login.LoginRepository
import com.santrucho.habilife.ui.util.Resource
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke() : Resource<Unit>{
        return try{
            repository.logout()
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}