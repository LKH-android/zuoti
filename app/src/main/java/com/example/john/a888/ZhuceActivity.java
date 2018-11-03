package com.example.john.a888;



/**
 * Created by john on 2018/5/9.
 */
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by john on 2018/5/6.
 */
public class  ZhuceActivity extends Activity {
    public static ZhuceActivity mainactivity;
    private ImageView imageView;
    Button button1;
    Button button2;
    Button button3;
    EditText editText;
    private EditText editText1;
    private Uri photoUri;

    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;

    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        mainactivity = this;
        imageView=(ImageView)findViewById(R.id.imageView);
        button2=(Button)findViewById(R.id.button3);
        button1=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.edittext);
        editText1 = (EditText) findViewById(R.id.password);
        button3=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alertDialog =new AlertDialog.Builder(ZhuceActivity.mainactivity).setTitle("提示").setMessage("请选择操作")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("拍摄", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doHandlerPhoto(PIC_FROM_CAMERA);

                            }
                        }).setNeutralButton("本地相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doHandlerPhoto(PIC_FROM＿LOCALPHOTO);
                            }
                        }).create();
                alertDialog.show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent();
                intent2.setClass(ZhuceActivity.this,Denglu.class);
                startActivity(intent2);
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(editable.length()>=3&&editable.length()<=5)
                        {
                            if (editText1.length()>=6&&editText1.length()<=12){

                                Intent intent=new Intent();
                                intent.setClass(ZhuceActivity.this,QuerenmimaActivity.class);
                                intent.putExtra("name",editText.getText().toString());
                                intent.putExtra("password", editText1.getText().toString());
                                startActivity(intent);
                                finish();

                            }
                            else { Dialog alerDialog1=new AlertDialog.Builder(ZhuceActivity.mainactivity).setTitle("提示").setMessage("请正确输入密码").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            }).create();
                                alerDialog1.show();


                            }
                        }
                        else {
                            Dialog alerDialog1=new AlertDialog.Builder(ZhuceActivity.mainactivity).setTitle("提示").setMessage("请正确输入用户名").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            }).create();
                            alerDialog1.show();
                            return;
                        }
                    }
                });



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
    private void doHandlerPhoto(int type)
    {
        try
        {
            //保存裁剪后的图片文件
            File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, "upload.jpeg");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            photoUri = Uri.fromFile(picFile);

            if (type==PIC_FROM＿LOCALPHOTO)
            {    Intent intent = getCropImageIntent();
                startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
            }else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
            }

        } catch (Exception e)
        {
            Log.i("HandlerPicError", "处理图片出现错误");
        }
    }

    /**
     * 调用图片剪辑程序
     */
    public Intent getCropImageIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        setIntentParams(intent);
        return intent;
    }

    /**
     * 启动裁剪
     */
    private void cropImageUriByTakePhoto() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        setIntentParams(intent);
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    }

    /**
     * 设置公用参数
     */
    private void setIntentParams(Intent intent)
    {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PIC_FROM_CAMERA: // 拍照
                try
                {
                    cropImageUriByTakePhoto();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case PIC_FROM＿LOCALPHOTO:
                try
                {
                    if (photoUri != null)
                    {
                        Bitmap bitmap = decodeUriAsBitmap(photoUri);
                        imageView.setImageBitmap(bitmap);
                        Intent intent=new Intent(ZhuceActivity.this,QuerenmimaActivity.class);
                        intent.putExtra("bitmap", bitmap);
                        startActivity(intent);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    private Bitmap decodeUriAsBitmap(Uri uri)
    {
        Bitmap bitmap = null;
        try
        {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
