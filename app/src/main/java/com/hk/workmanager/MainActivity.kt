package com.hk.workmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonOneTimeWork: Button
    private lateinit var buttonInitialDelay: Button
    private lateinit var btnPeriodicWork: Button
    private lateinit var buttonSetConstraints: Button
    private lateinit var workManager : WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        workManager = WorkManager.getInstance()
        buttonOneTimeWork = findViewById(R.id.btn_one_time_work)
        buttonInitialDelay = findViewById(R.id.btn_initial_delay)
        btnPeriodicWork = findViewById(R.id.btn_periodic_work)
        buttonSetConstraints = findViewById(R.id.btn_set_constraints)
        buttonOneTimeWork.setOnClickListener(this)
        buttonInitialDelay.setOnClickListener(this)
        btnPeriodicWork.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_one_time_work -> {
                val oneTimeWorkRequest = OneTimeWorkRequest.from(OneTimeWork::class.java)
                workManager.enqueue(oneTimeWorkRequest)
            }
            R.id.btn_initial_delay -> {
                val initialDelayRequest = OneTimeWorkRequestBuilder<OneTimeWork>()
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .build()
                workManager.enqueue(initialDelayRequest)
            }
            R.id.btn_periodic_work -> {
                //the work is scheduled with a 5s interval
                // we can not set the interval less than 15 minutes.
                val periodicWorkrRequest = PeriodicWorkRequestBuilder<OneTimeWork>(5, TimeUnit.SECONDS)
                        .build()
                workManager.enqueue(periodicWorkrRequest)
            }
            R.id.btn_set_constraints -> {
                val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresCharging(true)
                        .build()

                val constraintRequest: WorkRequest =
                        OneTimeWorkRequestBuilder<OneTimeWork>()
                                .setConstraints(constraints)
                                .build()

                workManager.enqueue(constraintRequest)
            }
        }
    }
}