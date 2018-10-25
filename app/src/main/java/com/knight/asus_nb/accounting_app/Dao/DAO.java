package com.knight.asus_nb.accounting_app.Dao;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.knight.asus_nb.accounting_app.Bean.ListViewBean;
import com.knight.asus_nb.accounting_app.Bean.RecordBean;

import java.math.BigDecimal;
import java.util.LinkedList;

public class DAO implements DaoInterface {

    private MySQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private Context context;
    private boolean flag;

    public DAO(Context context) {
        super();
        this.context = context;
        helper = new MySQLiteOpenHelper(context);
    }

    //添加记录
    @Override
    public boolean insertData(RecordBean rb) {
        database = helper.getWritableDatabase();
        flag = false;
        ContentValues vuales1 = new ContentValues();

        vuales1.put("uuid", rb.getUuid());
        vuales1.put("type", rb.getType());
        vuales1.put("amount", rb.getAmount());
        vuales1.put("time", rb.getTime());
        vuales1.put("day", rb.getDay());
        vuales1.put("remark", rb.getRemark());
        vuales1.put("recordtype", rb.getRecord_type());
        long i = database.insert("record", null, vuales1);
        if (i > 0) {
            flag = true;
            database.close();
        }
        database.close();
        return flag;
    }

    //删除记录
    @Override
    public boolean deleteDate(String time) {
        flag = false;
        database = helper.getWritableDatabase();
        int d = database.delete("record", "time=?", new String[]{time});
        if (d > 0) {
            flag = true;
            database.close();
        }
        database.close();
        return flag;
    }

    //更新数据
    @Override
    public boolean updateData(String time, RecordBean rb) {
        flag = false;
        database = helper.getWritableDatabase();
        boolean flag1 = deleteDate(time);
        boolean flag2 = insertData(rb);
        if (flag1 && flag2) {
            flag = true;
            database.close();
        }
        database.close();
        return false;
    }

    //查找消费记录数据
    @Override
    public LinkedList<ListViewBean> queryRecord(String day) {
        database = helper.getWritableDatabase();
        LinkedList<ListViewBean> rblist = new LinkedList<ListViewBean>();
        Cursor cs = database.rawQuery("select distinct * from record where day=? order by time asc", new String[]{day});
        // cs.moveToFirst();
        while (cs.moveToNext()) {
            ListViewBean lb = new ListViewBean();
            lb.setList_recordamount(cs.getString(cs.getColumnIndex("amount")));
            int recyclenum = cs.getInt(cs.getColumnIndex("recordtype"));
            if (recyclenum == 0)
                lb.setList_recordtype("-");
            else lb.setList_recordtype("+");
            lb.setList_time(cs.getString(cs.getColumnIndex("time")));
            lb.setList_type(cs.getString(cs.getColumnIndex("type")));
            lb.setList_icon(geBitMapByName("icon_" + lb.getList_type()));
            rblist.add(lb);
        }
        cs.close();
        database.close();
        return rblist;
    }

    //查找消费的日期
    @Override
    public LinkedList<String> queryRecordForDate() {
        database = helper.getWritableDatabase();
        LinkedList<String> datelist = new LinkedList<String>();
        Cursor css = database.rawQuery("select distinct * from record order by day asc", new String[]{});
        // css.moveToFirst();
        while (css.moveToNext()) {
            String date = css.getString(css.getColumnIndex("day"));
            if (!datelist.contains(date)) {
                datelist.add(date);
            }
        }
        css.close();
        database.close();
        return datelist;
    }

    //根据日期 ，查找某天的消费金额记录
    @Override
    public String queryRecordAmount(String day) {
        database = helper.getWritableDatabase();
        String amount = "";
        double sum = 0;
        Cursor cs = database.rawQuery("select distinct * from record where day=? order by time asc", new String[]{day});
        while (cs.moveToNext()) {
            //记录支出总额 和收入总额
            double expensenum = 0;
            double incomenum = 0;
            int symbol = cs.getInt(cs.getColumnIndex("recordtype"));
            if (symbol == 0) {
                expensenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            if (symbol == 1) {
                incomenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            sum += incomenum - expensenum;
        }
        BigDecimal b = new BigDecimal(sum);
        double newsum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        amount = String.valueOf(newsum);
        cs.close();
        database.close();

        return amount;
    }

    //查找全部消费金额
    @Override
    public String queryRecordallAmount() {
        database = helper.getWritableDatabase();
        String amount = "";
        double sum = 0;
        Cursor cs = database.rawQuery("select distinct * from record order by time asc", new String[]{});
        while (cs.moveToNext()) {
            //记录支出总额 和收入总额
            double expensenum = 0;
            double incomenum = 0;
            int symbol = cs.getInt(cs.getColumnIndex("recordtype"));
            if (symbol == 0) {
                expensenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            if (symbol == 1) {
                incomenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            sum += incomenum - expensenum;
        }
        BigDecimal b = new BigDecimal(sum);
        double newsum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        amount = String.valueOf(newsum);
        cs.close();
        database.close();
        return amount;
    }

    //查找收入总金额
    @Override
    public String queryRecordincomeAmount() {
        database = helper.getWritableDatabase();
        String amount = "";
        double sum = 0;
        Cursor cs = database.rawQuery("select distinct * from record order by time asc", new String[]{});
        while (cs.moveToNext()) {
            //记录收入总额
            double incomenum = 0;
            int symbol = cs.getInt(cs.getColumnIndex("recordtype"));
            if (symbol == 1) {
                incomenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            sum += incomenum;
        }
        BigDecimal b = new BigDecimal(sum);
        double newsum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        amount = String.valueOf(newsum);
        cs.close();
        database.close();
        return amount;
    }

    //查找支出总金额
    @Override
    public String queryReocrdexpenseAmount() {
        database = helper.getWritableDatabase();
        String amount = "";
        double sum = 0;
        Cursor cs = database.rawQuery("select distinct * from record order by time asc", new String[]{});
        while (cs.moveToNext()) {
            //记录支出总额
            double expensenum = 0;
            int symbol = cs.getInt(cs.getColumnIndex("recordtype"));
            if (symbol == 0) {
                expensenum += cs.getDouble(cs.getColumnIndex("amount"));
            }
            sum -= expensenum;
        }
        BigDecimal b = new BigDecimal(sum);
        double newsum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        amount = String.valueOf(newsum);
        cs.close();
        database.close();
        return amount;
    }

    //获取图片资源文件
    public int geBitMapByName(String name) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        int resID = context.getResources().getIdentifier(name, "mipmap", appinfo.packageName);
        return resID;
    }
}


