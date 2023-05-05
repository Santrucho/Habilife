package com.santrucho.habilife.ui.util.manager

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class HabitScheduler(private val context: Context) {

    private val updateFieldWorkerRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
        UpdateFieldWorker::class.java,1,TimeUnit.DAYS
    ).build()

    fun startUpdateFieldWorker(){
        WorkManager.getInstance(context).enqueue(updateFieldWorkerRequest)
    }
}