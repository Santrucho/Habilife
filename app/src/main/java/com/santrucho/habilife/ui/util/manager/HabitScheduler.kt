package com.santrucho.habilife.ui.util.manager

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class HabitScheduler(private val context: Context) {

    private fun getWorkStartTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.timeInMillis
    }

    private val updateFieldWorkerRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
        UpdateFieldWorker::class.java,1,TimeUnit.DAYS
    ).setInitialDelay(getWorkStartTime() - System.currentTimeMillis(),TimeUnit.MILLISECONDS)
        .build()

    fun startUpdateFieldWorker(){
        WorkManager.getInstance(context).enqueue(updateFieldWorkerRequest)
    }

}