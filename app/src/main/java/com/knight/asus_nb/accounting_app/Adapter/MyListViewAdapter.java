package com.knight.asus_nb.accounting_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.knight.asus_nb.accounting_app.Bean.ListViewBean;
import com.knight.asus_nb.accounting_app.R;

import org.xml.sax.helpers.LocatorImpl;

import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<ListViewBean> {
    private int newResourceID;
    private List<ListViewBean> list;

    public MyListViewAdapter(@NonNull Context context, int resource, @NonNull List<ListViewBean> list) {
        super(context, resource, list);
        newResourceID = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewBean listViewBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(newResourceID, parent, false);

        ImageView list_image = view.findViewById(R.id.list_icon);
        TextView list_type = view.findViewById(R.id.list_item_type);
        TextView list_time = view.findViewById(R.id.list_time);
        TextView list_recordamount = view.findViewById(R.id.list_recordamount);
        TextView list_recordtype = view.findViewById(R.id.list_recordtype);

        list_image.setImageResource(listViewBean.getList_icon());
        list_type.setText(listViewBean.getList_type());
        list_time.setText(listViewBean.getList_time());
        list_recordamount.setText(listViewBean.getList_recordamount());
        list_recordtype.setText(listViewBean.getList_recordtype());

        return view;

    }


}
