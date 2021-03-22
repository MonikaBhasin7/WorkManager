package com.hk.workmanager

/*
WorkManager
WorkManager falls into the category of guaranteed execution and deferrable.

Internally, WorkManager uses the existing mentioned options to
perform background work: JobScheduler, Firebase JobDispatcher, and Alarm Manager + Broadcast receivers.

  A few example use cases:
    -Uploading files to a server.
    -Syncing data to/from a server and saving it to a Room database.
    -Sending logs to a server.
    -Executing expensive operations on data.

    Understanding WorkManager Classes
    - WorkManager: This is the main class that you’ll use to enqueue WorkRequests.
    - Worker: You’ll need to subclass this to perform the task that will run in the background.
    - WorkRequest: This represents the request of the task that will run. You’ll set the task
                   through the Worker subclass. You can also specify constraints — for example,
                   only run the task if the device has Internet connectivity. There are two main types of work requests: OneTimeWorkRequest, which executes the task just one time, and PeriodicWorkRequest, which executes your task periodically. The period of a PeriodicWorkRequest can be customized.
    - WorkStatus: Whenever you need to know the status — running, enqueued, finished, etc.,
                  for example — of a particular WorkRequest, you can ask the WorkManager for it.
                  It will provide a LiveData object containing one or more WorkStatus objects.
                  *
                  *

   Step for implementation WorkManager to Schedule Tasks ->
   - Create a new project and add WorkManager dependency in app/buid.gradle file
   - Create a base class of Worker
   - Create WorkRequest
   - Enqueue the request with WorkManager.
   - Fetch the particular task status.



   1.Define the work(https://developer.android.com/topic/libraries/architecture/workmanager/basics#define_the_work)
    -- Work is defined using the Worker class. The doWork() method runs asynchronously on a background
       thread provided by WorkManager. To create some work for WorkManager to run, extend the Worker
       class and override the doWork() method. For example, to create a Worker that uploads images,
       you can do the following:

                    class UploadWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
                        override fun doWork(): Result {
                             // Do the work here--in this case, upload the images.
                             uploadImages()
                            // Indicate whether the work finished successfully with the Result
                             return Result.success()
                         }
                    }


         The Result returned from doWork() informs the WorkManager service whether the work succeeded
         and, in the case of failure, whether or not the work should be retried.

        Result.success(): The work finished successfully.
        Result.failure(): The work failed.
        Result.retry(): The work failed and should be tried at another time according to its retry policy.



    2. Create a WorkRequest(https://developer.android.com/topic/libraries/architecture/workmanager/basics#create_a_workrequest)
       -- Once your work is defined, it must be scheduled with the WorkManager service in order to run.
       WorkManager offers a lot of flexibility in how you schedule your work.
       You can schedule it to run periodically over an interval of time, or you can schedule it to
       run only one time.However you choose to schedule the work, you will always use a WorkRequest.
       While a Worker defines the unit of work, a WorkRequest (and its subclasses) define how and when
       it should be run. In the simplest case, you can use a OneTimeWorkRequest, as shown in the following example.

                val uploadWorkRequest: WorkRequest =
                        OneTimeWorkRequestBuilder<UploadWorker>()
                        .build()



     3. Submit the WorkRequest to the system(https://developer.android.com/topic/libraries/architecture/workmanager/basics#submit_the_workrequest_to_the_system)
        -- Finally, you need to submit your WorkRequest to WorkManager using the enqueue() method.
           The exact time that the worker is going to be executed depends on the constraints
           that are used in your WorkRequest and on system optimizations. WorkManager is designed
           to give the best behavior under these restrictions.

           WorkManager.getInstance(myContext).enqueue(uploadWorkRequest)


*/