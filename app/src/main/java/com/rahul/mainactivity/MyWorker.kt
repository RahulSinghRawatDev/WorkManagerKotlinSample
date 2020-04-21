package com.rahul.mainactivity

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context , workParams : WorkerParameters) : Worker(context,workParams){
    override fun doWork(): Result {
        Log.i("MyWorker",".....worker thread running.....")
        return Result.retry()
    }
}