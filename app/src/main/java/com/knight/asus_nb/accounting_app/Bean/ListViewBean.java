package com.knight.asus_nb.accounting_app.Bean;

import android.app.Application;

import com.knight.asus_nb.accounting_app.UI.MainActivity;

public class ListViewBean {
    private String list_type;
    private int list_icon;
    private String list_time;
    private String list_recordamount;
    private String list_recordtype;


    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }

    public int getList_icon() {
        return list_icon;
    }

    public void setList_icon(int list_icon) {
        this.list_icon = list_icon;
    }

    public String getList_time() {
        return list_time;
    }

    public void setList_time(String list_time) {
        this.list_time = list_time;
    }

    public String getList_recordamount() {
        return list_recordamount;
    }

    public void setList_recordamount(String list_recordamount) {
        this.list_recordamount = list_recordamount;
    }

    public String getList_recordtype() {
        return list_recordtype;
    }

    public void setList_recordtype(String list_recordtype) {
        this.list_recordtype = list_recordtype;
    }


}
