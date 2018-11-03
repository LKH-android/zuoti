package com.example.john.a888;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by john on 2018/6/21.
 */

public class guanliyuanActivity extends Activity {
    Button button,button1,button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanli);
        button = (Button) findViewById(R.id.guanlibutton1);
        button1 = (Button) findViewById(R.id.guanlibutton2);
        button2 = (Button) findViewById(R.id.guanlibutton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(guanliyuanActivity.this, tianjiatimuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(guanliyuanActivity.this,shanchutimuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(guanliyuanActivity.this,Denglu.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
