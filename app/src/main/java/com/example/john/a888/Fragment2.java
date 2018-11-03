package com.example.john.a888;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Fragment2 extends ListFragment {
    private ListView list;
    private SimpleAdapter adapter;
    final String[] listItem = new String[] { "语文", "数学","英语","物理","化学","生物","地理"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xueke, container, false);
        list = (ListView) view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new SimpleAdapter(getActivity(), getData(listItem),
                R.layout.item1, new String[] { "name" },
                new int[] { R.id.functionName1 });
        setListAdapter(adapter);

    }
    private List<? extends Map<String, ?>> getData(String[] strs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i <listItem.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", strs[i]);
            list.add(map);

        }

        return list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final TextView textView=(TextView)arg1.findViewById(R.id.functionName1);
                Dialog alertDialog=new AlertDialog.Builder(getActivity())
                        .setTitle("提示")

                        .setMessage("本次测试共有时间5分钟，共5题")

                        .setPositiveButton("测试",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        Intent intent=new Intent();
                                        intent.setClass(getActivity(),ExamActivity.class);
                                        intent.putExtra("xueke2", textView.getText().toString());
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                })
                        .setNeutralButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                    }
                                }).show();

            }
        });

    }



}