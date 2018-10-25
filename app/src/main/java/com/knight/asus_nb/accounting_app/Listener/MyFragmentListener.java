package com.knight.asus_nb.accounting_app.Listener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.knight.asus_nb.accounting_app.Adapter.MyPagerAdapter;
import com.knight.asus_nb.accounting_app.Dao.DAO;
import com.knight.asus_nb.accounting_app.Util.DateUtil;
import com.robinhood.ticker.TickerView;

import java.util.LinkedList;

public class MyFragmentListener implements ViewPager.OnPageChangeListener {
    private MyPagerAdapter adapter;
    private TickerView tickerView;
    private Context context;
    private static String amount;
    private LinkedList<String> dates;
    private DAO dao;
    private TextView weektext;

    public MyFragmentListener(TickerView tickerView, Context context, TextView weektext) {
        this.tickerView = tickerView;
        this.context = context;
        this.weektext = weektext;
        dao = new DAO(context);
        dates = dao.queryRecordForDate();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        String date = dates.get(i);
        amount = dao.queryRecordAmount(date);
        //监听改变金钱数额
        tickerView.setText(amount);
        //监听改变星期
        weektext.setText(DateUtil.Formateweek(date));

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


}
