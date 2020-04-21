# WorkManagerKotlinSample
<p><b>Best example to demonstrate workmanager and its working using Kotlin language.</b></p>

<h1>WorkManager</h1>

<p><b>WorkManager API makes it easy to schedule deferrable(not required to run immediately) or at specific task, asynchronous tasks that are expected to run even if app exists or device restarts. </b></p>
<p><b>It guarantee the task completion. by Guarantee it means it will take care of the logic to start your work under a variety of situations even if you navigate away from your application. </b></p>

<h2>Features.</h2>
<p><b>1. Uses JobScheduler on devices with API 23+.</b></p>
<p><b>2. Uses Combination of BroadCastReceiver + AlarmManager on device with API 14 -22.</b></p>
<p><b>3. You can define constraints like NetworkAvailability, charging status , deviceIDLE state or  storageNotLow etc. </b></p>
<p><b>4. Can Schedule one-off(one time) or periodic tasks. </b></p>
<p><b>5. Can Chain multiple tasks together example task a , b, c combine result will get to d task. </b></p>
<p><b>6. Using this Saves lots of battery as it respects doze mode. </b></p>
<p><b>7. Ensure task execution even if device restarts as it internally maintains a WorkManager Database of tasks. </b></p>

<h2>Examples.</h2>
<p><b>1. Uploading logs.</b></p>
<p><b>2. Periodic syncing local data with the network.</b></p>
<p><b>3. Apply filter to image and saving the image.</b></p>

<h2> State of OneTime Work </h2>
<p><b>1. It have two states BLOCKED or ENQUEUE.  </b></p>
<p><b>2. After step 1 it go to RUNNING state.  </b></p>
<p><b>3. IF worker return success then we will mark it with SUCCESS and same with FAILURE, CANNCELLED and RETRY.  </b></p>

<h2> State of Periodic Work </h2>
<p><b>1. It have only one state  ENQUEUE no BLOCKED state.  </b></p>
<p><b>2. One constraints are met it will go to RUNNING state.  </b></p>
<p><b>3. After step 2 , whether you mark it SUCCESS or RETRY you go to ENQUEUE state  </b></p>
<p><b>4. After step 2 , if you mark it FAILURE then periodic work won't run again.</b></p>
<p><b>5. Anytime your work will be in CANCEL state then periodic will not work again. </b></p>

<p><b> * In chaining, If a unit of work fail then all child works also FAIL and If a unit of work cancel then all its child work also CANCELS. </b></p>

<h2>Steps to add workManager to your app </h2>
<p><b>1. Add dependency in your app gradle file. Add ktx if using kotlin only.</b></p>
<code><pre>

dependencies {
    implementation "androidx.work:work-runtime-ktx:$versions.work"
}

</code></pre>
<p><b>2. Add worker class and override do work method and put your work inside this overrided method. </b></p>
<p><b> Extend Worker class if you want synchronous tasks do work works in specified background thread and Use ListenableWorker class if you want asynchronous tasks for that use startWork in main thread and do work in your own custom threads.  </b></p>

<code><pre>
class MyWorker(context: Context , workParams : WorkerParameters) : Worker(context,workParams){
    override fun doWork(): Result {
        Log.i("MyWorker",".....worker thread running.....")
        return Result.retry()
    }
}

class MyWorker(context: Context , workParams : WorkerParameters) : ListenableWorker(context,workParams){
    override fun startWork(): ListenableFuture<Result> {
        return FUTURE
 }
}

Here do work returns a Result which can be SUCCESS , FAILURE and RETRY
It Schedules to do work on background thread.

</code></pre>

<p><b>3. Add WorkRequest either onetime or periodic with optional constaints like Network availability or Network enable </b></p>

<code><pre>
// constaints that i want to run this thing only when device is in charging mode and Network type is connected

       val constaints : Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    // Use either of two

    //Use for periodic taks ( minimum time i 15 minutes)

        val periodicWork = PeriodicWorkRequestBuilder<MyWorker>(16,TimeUnit.MINUTES)
          .setConstraints(constaints).build()

    // use for one time tasks

          val oneTimeWork = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constaints).build()

</code></pre>

<p><b>4. Schedule the workReuest with WorkManager instance </p></b>
<code><pre>   WorkManager.getInstance().enqueue(< workReuest instance >)</code></pre>

