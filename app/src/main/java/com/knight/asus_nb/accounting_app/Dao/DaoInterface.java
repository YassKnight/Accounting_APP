package com.knight.asus_nb.accounting_app.Dao;

import com.knight.asus_nb.accounting_app.Bean.ListViewBean;
import com.knight.asus_nb.accounting_app.Bean.RecordBean;

import java.util.LinkedList;

public interface DaoInterface {
    public boolean insertData(RecordBean rb);

    public boolean deleteDate(String time);

    public boolean updateData(String uuid, RecordBean rb);

    public LinkedList<ListViewBean> queryRecord(String day);

    public LinkedList<String> queryRecordForDate();

    public String queryRecordAmount(String day);

    public String queryRecordallAmount();

    public String queryRecordincomeAmount();

    public String queryReocrdexpenseAmount();
}
