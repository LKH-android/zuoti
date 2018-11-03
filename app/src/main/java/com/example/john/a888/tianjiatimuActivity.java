package com.example.john.a888;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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

public class tianjiatimuActivity extends Activity {
    private SQLiteDatabase db;
    private String DB_NAME = "sjk.db";
    private String DB_PATH = "/data/data/com.example.john.a888/databases/";
    private int count;
    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8;
    Button button1,button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianjiatimu);
        button1=(Button)findViewById(R.id.tianjiabutton1);
        button2=(Button)findViewById(R.id.tianjiabutton2);
        editText1=(EditText)findViewById(R.id.tianjiatimu1);
        editText2=(EditText)findViewById(R.id.tianjiatimu2);
        editText3=(EditText)findViewById(R.id.tianjiatimu3);
        editText4=(EditText)findViewById(R.id.tianjiatimu4);
        editText5=(EditText)findViewById(R.id.tianjiatimu5);
        editText6=(EditText)findViewById(R.id.tianjiatimu6);
        editText7=(EditText)findViewById(R.id.tianjiatimu7);
        editText8=(EditText)findViewById(R.id.tianjiatimu8);
        initFile();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(tianjiatimuActivity.this,guanliyuanActivity.class);
                startActivity(intent);
                finish();
            }

        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = SQLiteDatabase.openDatabase("/data/data/com.example.john.a888/databases/sjk.db", null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues values = new ContentValues();
                values.put("Field2", editText1.toString());
                values.put("Field3", editText2.toString());
                values.put("Field4", editText3.toString());
                values.put("Field5", editText4.toString());
                values.put("Field6",editText5.toString());
                values.put("Field7",editText6.toString());
                values.put("Field8", editText7.toString());
                values.put("Field9",editText8.toString());
                 db.insert("question", null, values);
                new AlertDialog.Builder(tianjiatimuActivity.this).setTitle("提示").setMessage("添加成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(tianjiatimuActivity.this, guanliyuanActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
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
