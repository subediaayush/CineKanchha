package com.cinekancha.entities.service;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.cinekancha.activities.HomeActivity;

public class TestService extends JobService {

    JobParameters params;
    DoItTask doIt;

    @Override
    public boolean onStartJob(JobParameters params) {
        this.params = params;
        Log.d("TestService", "Work to be called from here");
        doIt = new DoItTask();
        doIt.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TestService", "System calling to stop the job here");
        if (doIt != null)
            doIt.cancel(true);
        return false;
    }

    private class DoItTask extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("DoItTask", "Clean up the task here and call jobFinished...");
            jobFinished(params, false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("DoItTask", "Working here...");
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationUtils.showNotificationMessage("Movies Review", "New Movies in town.", "7", intent, null);
            return null;
        }
    }

}