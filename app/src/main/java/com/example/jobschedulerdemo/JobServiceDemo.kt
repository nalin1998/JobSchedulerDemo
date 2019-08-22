package com.example.jobschedulerdemo

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

const val TAG = "ExampleJobService"

class JobServiceDemo : JobService() {
    //we have to implement 2 methods -- onStartJob onStopJob....

    //we need to close our Job ourself otherwise our app will misbehave.....jobCancelled variable is used for that purpose
    private var jobCancelled: Boolean = false

    //this method is called when system executes our Job
    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.v(TAG, "[ onStartJob ]")
        doBackgroundWork(p0)
        //return false if task is short and can be executed in the scope of this method
        //return true if ur task is something very long and needs to keep running in background
        return true

    }

    //this is our background work ..called inside onStartJob
    private fun doBackgroundWork(p0: JobParameters?) {

        Log.v(TAG, "[ doBackgroundWork method ]")

        Thread(Runnable {
            for (i in 1..10) {
                if (jobCancelled) {
                    return@Runnable
                }
                Log.d(TAG, "run :$i")
                Thread.sleep(1000)

            }
            Log.d(TAG, " Job is finished ")
            //job finished method tells the system that the job is finished
            jobFinished(p0, false)
        }).start()
    }

    //this method will be called by system when our job will be cancelled
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, " [ onStopJob ] ")

        jobCancelled = true
        //if we need to reschedule our job later return true else return false
        return false
    }


}