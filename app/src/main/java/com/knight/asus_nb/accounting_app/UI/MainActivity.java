package com.knight.asus_nb.accounting_app.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.knight.asus_nb.accounting_app.Adapter.MyPagerAdapter;
import com.knight.asus_nb.accounting_app.Dao.DAO;
import com.knight.asus_nb.accounting_app.Listener.MyFragmentListener;
import com.knight.asus_nb.accounting_app.R;
import com.knight.asus_nb.accounting_app.Util.DateUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private SlidingMenu slidingMenu;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private ImageView userinfo_view;
    private TickerView tickerView;//金额显示
    private TextView weektext;//星期显示
    private TextView incometext; //侧边栏支出收入显示
    private TextView expensetext;
    private DatePickerDialog datePickerDialog;
    private String selectday;//日历选择的日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitSlidingMenu();


        //设置星期
        weektext = findViewById(R.id.week_text);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String week = simpleDateFormat.format(date);
        weektext.setText(DateUtil.Formateweek(week));

        //标题栏
        userinfo_view = findViewById(R.id.userinfo);

        //侧边栏
        imageView = slidingMenu.findViewById(R.id.myicon);




        tickerView = findViewById(R.id.ticker_view);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());

        //ViewPager初始化，设置适配器
        viewPager = findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), this,tickerView,incometext,expensetext);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getIndex());

        //viewpager设置滑动监听
        viewPager.setOnPageChangeListener(new MyFragmentListener(tickerView, this, weektext));


        //设置当天金额
        DAO dao = new DAO(this);
        tickerView.setText(dao.queryRecordAmount(adapter.getItem(adapter.getIndex()).getDate()));
        //设置动画效果
        tickerView.setAnimationInterpolator(new OvershootInterpolator());
        tickerView.setAnimationDuration(1500);

    }

    //初始化侧边栏
    public void InitSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setMenu(R.layout.slidingmenu);

        incometext = findViewById(R.id.incomeamount);
        expensetext = findViewById(R.id.expenseamount);
        //设置金额
        incometext.setText(new DAO(this).queryRecordincomeAmount());
        expensetext.setText(new DAO(this).queryReocrdexpenseAmount());
    }

    //主页按钮点击事件
    public void myclick(View view) {
        switch (view.getId()) {
            case R.id.userinfo:
                slidingMenu.showMenu();
                break;
            case R.id.calendar:
                //初始化日历Dialog
                initDatePickerDialog();
                break;
            case R.id.add_btn:
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.myicon:
                Toast.makeText(MainActivity.this, "点击了头像", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //初始化日历
    public void initDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yyyy, int MM, int dd) {
                //修改日历获取的日期格式
                if (MM < 9)
                    if (dd < 10)
                        selectday = yyyy + "-" + "0" + (MM + 1) + "-" + "0" + dd;
                    else if (dd >= 10)
                        selectday = yyyy + "-" + "0" + (MM + 1) + "-" + dd;
                if (MM >= 9)
                    if (dd < 10)
                        selectday = yyyy + "-" + (MM + 1) + "-" + "0" + dd;
                    else if (dd >= 10)
                        selectday = yyyy + "-" + (MM + 1) + "-" + dd;
                //查找是否存在查找的日期的记录
                for (int i = 0; i <= adapter.getIndex(); i++) {

                    if (adapter.getItem(i).getDate().trim().equals(selectday.trim())) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                    // if (adapter.getItem(adapter.getIndex()).getDate().trim() != selectday.trim())
                    // Toast.makeText(MainActivity.this, selectday + "，这一天没有记录", Toast.LENGTH_SHORT).show();

                }
            }
        }, year, monthOfYear, dayOfMonth);
        DatePicker dp = datePickerDialog.getDatePicker();
        dp.setMinDate(StringToLong(adapter.getItem(0).getDate().toString().trim()));
        // dp.setBackgroundColor(Color.GRAY);

        //设置日历最大可选择日期
        int nowmonthOfYear = monthOfYear + 1;
        dp.setMaxDate(StringToLong(year + "-" + nowmonthOfYear + "-" + dayOfMonth));

        datePickerDialog.show();
    }

    //String类型日期转化为长整型数据
    public long StringToLong(String dStr) {
        long time = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(dStr);
            time = d.getTime();
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }

        return time;
    }
}
