package com.example.john.a888;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by john on 2018/5/9.
 */

public class Denglu extends Activity {
    private EditText editText;
    private boolean isHideFirst = true;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu);
        final EditText editText1=(EditText)findViewById(R.id.dengluname);
        editText=(EditText)findViewById(R.id.denglupassword);
        Button button1=(Button)findViewById(R.id.denglubutton);
        button=(Button)findViewById(R.id.dengluzhuce);
        Button button2=(Button)findViewById(R.id.guanliyuan);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText1.getText().toString().trim();
                String pass=editText.getText().toString().trim();
                if(name.equals("root")&&pass.equals("root"))
                {
                   Intent intent=new Intent();
                   intent.setClass(Denglu.this,guanliyuanActivity.class);
                   startActivity(intent);
                   finish();
                }
                else{
                    DisplayToast("管理员名或密码输入错误");
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Denglu.this,ZhuceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText1.getText().toString().trim();
                String pass=editText.getText().toString().trim();
                int result=SqliteDB.getInstance(getApplicationContext()).Quer(pass,name);
                if (result==1)
                {
                    DisplayToast("登陆成功");
                    Intent intent=new Intent();
                    intent.setClass(Denglu.this,MainActivity.class);
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
        final Drawable[] drawables = editText.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();// 眼睛图标的宽度
        final Drawable drawableEyeOpen = getResources().getDrawable(R.drawable.lol_icon_eye_open);
        drawableEyeOpen.setBounds(drawables[2].getBounds());
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX=view.getWidth()-eyeWidth-editText.getPaddingRight();
                    float et_pwdMaxX=view.getWidth();
                    float et_pwdMinY=0;
                    float et_pwdMaxY=view.getHeight();
                    float x=motionEvent.getX();
                    float y=motionEvent.getY();
                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY){
                        isHideFirst=!isHideFirst;
                        if(isHideFirst){
                            editText.setCompoundDrawables(null,null,drawables[2],null);
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }else {
                            editText.setCompoundDrawables(null,null,drawableEyeOpen,null);
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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
