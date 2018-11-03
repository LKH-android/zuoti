package com.example.john.a888;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by john on 2018/5/9.
 */

public class HuangyingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huangyingjiemian);

        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(HuangyingActivity.this,Denglu.class);
                startActivity(intent);
                HuangyingActivity.this.finish();
            }
        };

        timer.schedule(task,3500);

    }
}
