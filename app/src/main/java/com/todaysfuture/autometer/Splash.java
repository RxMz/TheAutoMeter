package com.todaysfuture.autometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by rishabh on 15/5/16.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashh);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(2000);

                }catch (InterruptedException e){
                    e.printStackTrace();

                }finally {
                    Intent yolo=new Intent("android.intent.action.ChooseCity");
                    startActivity(yolo);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
