package com.example.john.a888;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2018/5/19.
 */

public class ZuotiActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private String DB_NAME = "sjk.db";
    //数据库的地址
    private String DB_PATH = "/data/data/com.example.john.a888/databases/";
    //总的题目数据
    private int count;
    //当前显示的题目
    private int corrent;
    //问题
    private TextView tv_title;
    //选项
    RadioButton[] mRadioButton = new RadioButton[4];
    //上一题
    private Button btn_up;
    //下一题
    private Button btn_down;
    //详情
    private TextView tv_result;
    //容器
    private RadioGroup mRadioGroup;
    //是否进入错题模式
    private boolean wrongMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zuoti);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        String value = intent.getStringExtra("xueke1");

        initFile();
        initView();
        initDB();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                new AlertDialog.Builder(ZuotiActivity.this).setTitle("提示").setMessage("确认退出练习？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(ZuotiActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("取消", null).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化View
     */
    private void initView() {

        wrongMode = false;

        tv_title = (TextView) findViewById(R.id.tv_title);

        mRadioButton[0] = (RadioButton) findViewById(R.id.RadioA);
        mRadioButton[1] = (RadioButton) findViewById(R.id.RadioB);
        mRadioButton[2] = (RadioButton) findViewById(R.id.RadioC);
        mRadioButton[3] = (RadioButton) findViewById(R.id.RadioD);

        btn_down = (Button) findViewById(R.id.btn_down);
        btn_up = (Button) findViewById(R.id.btn_up);

        tv_result = (TextView) findViewById(R.id.tv_result);

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
    }
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();
        final Intent intent = getIntent();

        db = SQLiteDatabase.openDatabase("/data/data/com.example.john.a888/databases/sjk.db", null, SQLiteDatabase.OPEN_READWRITE);
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from question where Field9=?", new String[]{intent.getStringExtra("xueke1")});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                //ID
                question.ID = cursor.getInt(cursor.getColumnIndex("Field1"));
                //问题
                question.question = cursor.getString(cursor.getColumnIndex("Field2"));
                //四个选择
                question.answerA = cursor.getString(cursor.getColumnIndex("Field3"));
                question.answerB = cursor.getString(cursor.getColumnIndex("Field4"));
                question.answerC = cursor.getString(cursor.getColumnIndex("Field5"));
                question.answerD = cursor.getString(cursor.getColumnIndex("Field6"));
                //答案
                question.answer = cursor.getInt(cursor.getColumnIndex("Field7"));
                //解析
                question.explaination = cursor.getString(cursor.getColumnIndex("Field8"));
                //设置为没有选择任何选项
                question.selectedAnswer = -1;
                list.add(question);
            }
        }
        return list;

    }

    /**
     * 初始化数据库服务
     */
    private void initDB() {



        final List<Question> list = getQuestion();

        count = list.size();
        corrent = 0;

        Question q = list.get(0);
        tv_title.setText(q.question);

        mRadioButton[0].setText(q.answerA);
        mRadioButton[1].setText(q.answerB);
        mRadioButton[2].setText(q.answerC);
        mRadioButton[3].setText(q.answerD);

        //上一题
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corrent > 0) {
                    corrent--;

                    Question q = list.get(corrent);

                    tv_title.setText(q.question);

                    mRadioButton[0].setText(q.answerA);
                    mRadioButton[1].setText(q.answerB);
                    mRadioButton[2].setText(q.answerC);
                    mRadioButton[3].setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (q.selectedAnswer != -1) {
                        mRadioButton[q.selectedAnswer].setChecked(true);
                    }
                }

            }
        });

        //下一题
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if (corrent < count - 1) {
                    corrent++;
                    Question q = list.get(corrent);

                    tv_title.setText(q.question);

                    mRadioButton[0].setText(q.answerA);
                    mRadioButton[1].setText(q.answerB);
                    mRadioButton[2].setText(q.answerC);
                    mRadioButton[3].setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (q.selectedAnswer != -1) {
                        mRadioButton[q.selectedAnswer].setChecked(true);
                    }
                } else if (corrent == count - 1 && wrongMode == true) {

                    new AlertDialog.Builder(ZuotiActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(ZuotiActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).setNegativeButton("取消", null).show();

                } else {
                    //没有题目了，开始检测正确性
                    final List<Integer> wrongList = checkAnswer(list);

                    if (wrongList.size() == 0) {
                        new AlertDialog.Builder(ZuotiActivity.this).setTitle("提示").setMessage("你好厉害，答对了所有题！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent();
                                        intent1.setClass(ZuotiActivity.this, MainActivity.class);
                                        startActivity(intent1);
                                        finish();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent2 = new Intent();
                                intent2.setClass(ZuotiActivity.this, MainActivity.class);
                                startActivity(intent2);
                                finish();
                            }
                        }).show();
                    }

                    //窗口提示
                    new AlertDialog.Builder(ZuotiActivity.this).setTitle("恭喜，答题完成！")
                            .setMessage("答对了" + (list.size() - wrongList.size()) + "道题" + "\n"
                                    + "答错了" + wrongList.size() + "道题" + "\n" + "是否查看错题？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wrongMode = true;
                            List<Question> newList = new ArrayList<Question>();
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(list.get(wrongList.get(i)));

                            }
                            list.clear();
                            for (int i = 0; i < newList.size(); i++) {
                                list.add(newList.get(i));
                            }
                            corrent = 0;
                            count = list.size();

                            //更新当前显示的内容
                            Question q = list.get(corrent);

                            tv_title.setText(q.question);

                            mRadioButton[0].setText(q.answerA);
                            mRadioButton[1].setText(q.answerB);
                            mRadioButton[2].setText(q.answerC);
                            mRadioButton[3].setText(q.answerD);

                            tv_result.setText(q.explaination);
                            //显示结果
                            tv_result.setVisibility(View.VISIBLE);


                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(ZuotiActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
                }
            }
        });

        //答案选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (mRadioButton[i].isChecked() == true) {
                        list.get(corrent).selectedAnswer = i;
                        break;
                    }
                }
            }
        });
    }

    /**
     * 判断是否答题正确
     *
     * @param
     * @return
     */
    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //判断对错
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

    /**
     * 将数据库拷贝到相应目录
     */
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

