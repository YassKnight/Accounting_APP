package com.knight.asus_nb.accounting_app.Adapter;

import com.knight.asus_nb.accounting_app.Bean.RecycleItemBean;
import com.knight.asus_nb.accounting_app.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context mcontext;
    private ArrayList<RecycleItemBean> datalist;
    private OnItemClickListener onItemClickListener;

    public MyRecycleAdapter(Context context, ArrayList<RecycleItemBean> datalist) {
        this.mcontext = context;
        this.datalist = datalist;
        inflater = LayoutInflater.from(mcontext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.myrecycle_item, null);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.imageView.setImageResource(datalist.get(i).getRecycleItemImg());
        myViewHolder.textView.setText(datalist.get(i).getRecycleItemText());

        if (onItemClickListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onclick(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.recycle_item_image);
            textView = view.findViewById(R.id.recycle_item_text);
        }
    }


    //内置监听方法
    public interface OnItemClickListener {
        void onclick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
