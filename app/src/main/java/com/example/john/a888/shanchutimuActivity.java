package com.example.john.a888;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by john on 2018/6/21.
 */

public class shanchutimuActivity extends Activity {
    private SQLiteDatabase db;
    private String DB_NAME = "sjk.db";
    //数据库的地址
    private String DB_PATH = "/data/data/com.example.john.a888/databases/";
    EditText editText1;
    Button button1,button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shanchutimu);
        editText1=(EditText)findViewById(R.id.shanchutimu);
        button1=(Button)findViewById(R.id.shanchubutton1);
        button2=(Button)findViewById(R.id.shanchubutton2);
        initFile();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = SQLiteDatabase.openDatabase("/data/data/com.example.john.a888/databases/sjk.db", null, SQLiteDatabase.OPEN_READWRITE);
                Cursor cursor = db.rawQuery("select * from question where Field1=?", new String[]{editText1.getText().toString()});
                //执行sql语句
                if (cursor.getCount() > 0) {
                db.delete("question", "Field1=?", new String[]{editText1.getText().toString()});
                new AlertDialog.Builder(shanchutimuActivity.this).setTitle("提示").setMessage("删除成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(shanchutimuActivity.this, guanliyuanActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();}
                        else {
                    new AlertDialog.Builder(shanchutimuActivity.this).setTitle("提示").setMessage("没有此数据")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(shanchutimuActivity.this,guanliyuanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initFile() {
        //判断数据库是否拷贝到相应的目录下
        if (new File(DB_PATH + DB_NAME).exists() == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //复制文件
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                //用来复制文件
                byte[] buffer = new byte[1024];
                //保存已经复制的长度
                int length;

                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
