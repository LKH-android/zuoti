package com.example.john.a888;

/**
 * Created by john on 2018/5/9.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by john on 2018/5/8.
 */

public class QuerenmimaActivity extends Activity {
    private EditText editText3;
    SQLiteDatabase db;
    Button button2;
    public static QuerenmimaActivity mainactivity;
    Button button;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querenmima);
        mainactivity=this;
        final Intent intent = getIntent();
        final String value1 = intent.getStringExtra("password");
        final String value=intent.getStringExtra("name");
        final Bitmap value2=intent.getParcelableExtra("bitmap");
        button2=(Button)findViewById(R.id.button5);
        editText3=(EditText)findViewById(R.id.querenpassword);
        button=(Button)findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent();
                intent2.setClass(QuerenmimaActivity.this,ZhuceActivity.class);
                startActivity(intent2);
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editText3.getText().toString().equals(value1))
                        {

                            String name=value.toString().trim();
                            String pass=value1.toString().trim();
                            User user=new User();
                            user.setUsername(name);
                            user.setUserpwd(pass);
                            int result=SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                            if (result==1){

                                Dialog alerDialog1=new AlertDialog.Builder(QuerenmimaActivity.mainactivity).setTitle("提示").setMessage("注册成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent1=new Intent();
                                        intent1.setClass(QuerenmimaActivity.this,MainActivity.class);
                                        startActivity(intent1);
                                        finish();

                                    }
                                }).create();
                                alerDialog1.show();

                            }else  if (result==-1)
                            {
                                Dialog alerDialog1=new AlertDialog.Builder(QuerenmimaActivity.mainactivity).setTitle("提示").setMessage("用户名已存在").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                    }
                                }).create();
                                alerDialog1.show();
                            }
                            else
                            {
                                Dialog alerDialog1=new AlertDialog.Builder(QuerenmimaActivity.mainactivity).setTitle("提示").setMessage("注册失败").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                    }
                                }).create();
                                alerDialog1.show();

                            }
                        }
                        else {
                            Dialog alerDialog2=new AlertDialog.Builder(QuerenmimaActivity.mainactivity).setTitle("提示").setMessage("两次密码输入不一致").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();


                                }
                            }).create();
                            alerDialog2.show();
                        }
                    }
                });


            }
        });
        final Drawable[] drawables = editText3.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();// 眼睛图标的宽度
        final Drawable drawableEyeOpen = getResources().getDrawable(R.drawable.lol_icon_eye_open);
        drawableEyeOpen.setBounds(drawables[2].getBounds());//这一步不能省略
        editText3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX=view.getWidth()-eyeWidth-editText3.getPaddingRight();
                    float et_pwdMaxX=view.getWidth();
                    float et_pwdMinY=0;
                    float et_pwdMaxY=view.getHeight();
                    float x=motionEvent.getX();
                    float y=motionEvent.getY();
                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY){
                        isHideFirst=!isHideFirst;
                        if(isHideFirst){
                            editText3.setCompoundDrawables(null,null,drawables[2],null);
                            editText3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }else {
                            editText3.setCompoundDrawables(null,null,drawableEyeOpen,null);
                            editText3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
        });

    }
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {

                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
