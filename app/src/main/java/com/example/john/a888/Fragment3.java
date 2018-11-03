package com.example.john.a888;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by IT-CTY on 2018/4/25.
 */

public class Fragment3 extends Fragment {

    private Button button1;
    private Button button2;
    private Button button3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shezhi,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button3=(Button)getActivity().findViewById(R.id.button8);
        button1=(Button)getActivity().findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),XiugaimimaActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("确认注销");     //设置对话框标题


                builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                       Intent intent=new Intent();
                       intent.setClass(getActivity(),Denglu.class);
                       startActivity(intent);
                       getActivity().finish();

                    }

                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消

                AlertDialog dialog = builder.create();  //创建对话框

                dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏

                dialog.show();
            }
        });


    }
}