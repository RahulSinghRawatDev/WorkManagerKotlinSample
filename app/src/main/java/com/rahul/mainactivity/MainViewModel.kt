package com.rahul.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel(){

    init {
        val constaints : Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // use for perioddic tasks
        val periodicWork = PeriodicWorkRequestBuilder<MyWorker>(16,TimeUnit.MINUTES)
            .setConstraints(constaints).build()

        // use for one time tasks
        val oneTimeWork = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constaints).build()



       WorkManager.getInstance().enqueue(oneTimeWork)
    }


}