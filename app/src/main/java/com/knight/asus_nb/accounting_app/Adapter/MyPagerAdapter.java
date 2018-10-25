package com.knight.asus_nb.accounting_app.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;

import com.knight.asus_nb.accounting_app.Bean.ListViewBean;
import com.knight.asus_nb.accounting_app.Dao.DAO;
import com.knight.asus_nb.accounting_app.UI.MyFragment;
import com.knight.asus_nb.accounting_app.UI.SelectActivity;
import com.robinhood.ticker.TickerView;

import java.time.Year;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private LinkedList<MyFragment> mfragmentList = new LinkedList<>();
    private LinkedList<String> dates = new LinkedList<>();
    private Context context;
    private TickerView tickerView;
    private TextView textView1;
    private TextView textView2;

    public MyPagerAdapter(FragmentManager fm, Context context, TickerView tickerView, TextView textView1,TextView textView2) {
        super(fm);
        this.context = context;
        this.tickerView=tickerView;
        this.textView1=textView1;
        this.textView2=textView2;
        initAdapter();
    }

    @Override
    public MyFragment getItem(int i) {
        return mfragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }

    public void initAdapter() {
        DAO dao = new DAO(context);
        dates = dao.queryRecordForDate();

        for (String date : dates) {
            MyFragment fragment = new MyFragment(date, context,tickerView,textView1,textView2);
            mfragmentList.add(fragment);

        }
    }

    public int getIndex() {
        return mfragmentList.size() - 1;
    }


}
