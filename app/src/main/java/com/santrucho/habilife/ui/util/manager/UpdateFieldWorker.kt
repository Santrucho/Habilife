package com.santrucho.habilife.ui.util.manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.santrucho.habilife.ui.data.remote.habits.HabitsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class UpdateFieldWorker @AssistedInject constructor(private val repository : HabitsRepository, @Assisted context: Context, @Assisted workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "UpdateFieldWorker started.")
        repository.resetField()
        return Result.success()
    }
    companion object {
        private const val TAG = "UpdateFieldWorker"
    }
}