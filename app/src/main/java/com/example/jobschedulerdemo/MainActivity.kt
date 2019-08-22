package com.example.jobschedulerdemo

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

const val JobID = 12345

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("Main Activity" , "[ onCreate ]")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun scheduleJob (mView : View){
        Log.v("Main Activity" , " onClick Schedule Job ")
        val componentNameObject = ComponentName(this, JobServiceDemo::class.java)
        val info : JobInfo = JobInfo.Builder(JobID,componentNameObject)
           /* .setRequiresCharging(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)*/
            .setPeriodic(15*60*1000)
            .build()
        val jobScheduler : JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        var resultCode : Int = jobScheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.v("Main Activity" , "Job scheduled")
        }else{
            Log.v("Main Activity" , "Job Scheduling failed")
        }

    }


    fun cancelJob (mView : View){
        Log.v("Main Activity" , " onClick Cancel Job ")
        val jobScheduler : JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JobID)
        Log.v("Main Activity" , "Job Cancelled")
    }
}
