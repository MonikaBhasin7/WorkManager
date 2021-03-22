package com.hk.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OneTimeWork(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    val TAG = "OneTimeWork"
    override fun doWork(): Result {
        for(i in 1..10) {
            Log.d(TAG, i.toString())
        }
        return Result.success()
    }
}