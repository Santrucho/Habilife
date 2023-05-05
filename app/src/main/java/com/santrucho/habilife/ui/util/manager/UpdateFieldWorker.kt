package com.santrucho.habilife.ui.util.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import javax.inject.Inject

class UpdateFieldWorker @Inject constructor(private val repository : HabitsRepository, context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        repository.resetField()
        return Result.success()
    }
    companion object {
        private const val TAG = "UpdateFieldWorker"
    }
}