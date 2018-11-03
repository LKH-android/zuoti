package com.example.john.a888;

import android.app.Activity;

/**
 * Created by john on 2018/6/6.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by john on 2018/5/13.
 */

public class XiugaimimaActivity extends Activity {
    public static XiugaimimaActivity mainactivity;
    EditText editText1;
    EditText editText2,editText;
    Button button1;
    Button button2;
    private boolean isHideFirst = true;
    private boolean isHideFirst1 = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiugaimima);
        editText1=(EditText)findViewById(R.id.yuanmima);
        editText2=(EditText)findViewById(R.id.xinmima);
        editText=(EditText)findViewById(R.id.yonghuming);
        button1=(Button)findViewById(R.id.button10);
        button2=(Button)findViewById(R.id.button11);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(XiugaimimaActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText.getText().toString().trim();
                String pass=editText1.getText().toString().trim();
                //userList=SqliteDB.getInstance(getApplicationContext()).loadUser();
                int result=SqliteDB.getInstance(getApplicationContext()).Quer(pass,name);
                if (result==1)
                {
                    ContentValues values = new ContentValues();
                    values.put("userpwd",editText2.getText().toString());
                    OpenHelper dbhelper = new OpenHelper(XiugaimimaActivity.this, "sqlite_dbname", null, 1);
                    //得到可写的SQLiteDatabase对象
                    SQLiteDatabase db = dbhelper.getWritableDatabase();
                    //调用insert方法，将数据插入数据库
                    //参数3：where 子句 "?"是占位符号，对应后面的"1",这和web开发时的语法是一样的
                    db.update("User", values, "username=?", new String[]{editText.getText().toString()});
                    DisplayToast("修改密码成功");
                    Intent intent=new Intent();
                    intent.setClass(XiugaimimaActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (result==0){
                    DisplayToast("请正确输入用户名和密码");


                }
                else if(result==-1)
                {   DisplayToast("密码错误");
                }



            }
        });



        final Drawable[] drawables = editText1.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();// 眼睛图标的宽度
        final Drawable drawableEyeOpen = getResources().getDrawable(R.drawable.lol_icon_eye_open);
        drawableEyeOpen.setBounds(drawables[2].getBounds());//这一步不能省略
        editText1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX=view.getWidth()-eyeWidth-editText1.getPaddingRight();
                    float et_pwdMaxX=view.getWidth();
                    float et_pwdMinY=0;
                    float et_pwdMaxY=view.getHeight();
                    float x=motionEvent.getX();
                    float y=motionEvent.getY();
                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY){
                        isHideFirst=!isHideFirst;
                        if(isHideFirst){
                            editText1.setCompoundDrawables(null,null,drawables[2],null);
                            editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }else {
                            editText1.setCompoundDrawables(null,null,drawableEyeOpen,null);
                            editText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
        });
        editText2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX=view.getWidth()-eyeWidth-editText2.getPaddingRight();
                    float et_pwdMaxX=view.getWidth();
                    float et_pwdMinY=0;
                    float et_pwdMaxY=view.getHeight();
                    float x=motionEvent.getX();
                    float y=motionEvent.getY();
                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY){
                        isHideFirst1=!isHideFirst1;
                        if(isHideFirst1){
                            editText2.setCompoundDrawables(null,null,drawables[2],null);
                            editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }else {
                            editText2.setCompoundDrawables(null,null,drawableEyeOpen,null);
                            editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
        });
    }

    public void DisplayToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
}
