package com.example.ex20;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextCha;

    public MyAsyncTask(Activity contextCha) {
        this.contextCha = contextCha;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(contextCha, "OnPreExecute", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i=0;i<=100;i++) {
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressBar = (ProgressBar) contextCha.findViewById(R.id.progressBar);
        int value = values[0];
        progressBar.setProgress(value);
        TextView textView = contextCha.findViewById(R.id.txtProgess);
        textView.setText(value + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(contextCha, "OnPostExecute", Toast.LENGTH_SHORT).show();
    }
}
