package com.knight.asus_nb.accounting_app.UI;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.knight.asus_nb.accounting_app.Adapter.MyListViewAdapter;
import com.knight.asus_nb.accounting_app.Bean.ListViewBean;
import com.knight.asus_nb.accounting_app.Dao.DAO;
import com.knight.asus_nb.accounting_app.R;
import com.robinhood.ticker.TickerView;

import org.w3c.dom.Text;
import org.xml.sax.helpers.LocatorImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MyFragment extends Fragment {
    private View view;
    private ListView listView;
    private LinkedList<ListViewBean> arraylist;
    private static TextView dateTextview;
    private String date;
    private Context context;
    private MyListViewAdapter listViewAdapter;
    private TickerView tickerView;
    private TextView textView1;
    private TextView textView2;

    @SuppressLint("ValidFragment")
    public MyFragment(String date, Context context, TickerView tickerView, TextView textView1, TextView textView2) {
        this.date = date;
        this.context = context;
        this.tickerView = tickerView;
        this.textView1 = textView1;
        this.textView2 = textView2;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, null);
        dateTextview = view.findViewById(R.id.date_text);
        Initlist();
        listView = view.findViewById(R.id.mylistview);
        listViewAdapter = new MyListViewAdapter(getActivity(), R.layout.mylist_item, arraylist);
        listView.setAdapter(listViewAdapter);
        //lisView设置长按监听
        setListViewlistener();
        return view;
    }

    //初始化listview的内容Arraylist
    public void Initlist() {
        DAO dao = new DAO(context);
        arraylist = dao.queryRecord(date);
        dateTextview.setText(date);
    }

    //获取当前日期
    public String getDate() {
        return date;
    }

    public String getDateTextview() {
        return dateTextview.getText().toString();
    }

    //listView监听
    public void setListViewlistener() {
        //设置listview长按监听
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("编辑")
                        .setIcon(R.mipmap.ic_launcher)
                        .setNeutralButton("Edit", new DialogInterface.OnClickListener() {//添加修改按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean flag = false;
                                DAO dao = new DAO(getContext());
                                dao.deleteDate(arraylist.get(position).getList_time());
                                Intent intent = new Intent();
                                intent.putExtra("date", date);
                                intent.setClass(getActivity(), SelectActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {//添加删除按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean flag = false;
                                DAO dao = new DAO(getContext());
                                flag = dao.deleteDate(arraylist.get(position).getList_time());
                                if (flag) {
                                    arraylist.remove(position);
                                    listViewAdapter.notifyDataSetChanged();
                                    listView.setAdapter(listViewAdapter);
                                    tickerView.setText(new DAO(getContext()).queryRecordAmount(date).toString());
                                    //设置金额
                                   textView1.setText(new DAO(getContext()).queryRecordincomeAmount());
                                    textView2.setText(new DAO(getContext()).queryReocrdexpenseAmount());
                                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog.show();
                return true;
            }
        });
    }
}
