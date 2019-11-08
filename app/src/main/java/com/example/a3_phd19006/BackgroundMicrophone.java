package com.example.a3_phd19006;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundMicrophone extends Service {

    private MediaRecorder myRecorder;
    private String outputFile = null;
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        // super.onCreate();  (not sure about this one)

        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy_hhmmss");
        String format = s.format(new Date());

        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() +  "/" + format + ".3gpp";

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outputFile);

        try {
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }


        //90 minutes timer to stop the recording after 90 minutes

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //onCreate();
        // return 1;
        return START_STICKY; //not sure about this one
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {



    }
    public void onPause() {


    }

}
