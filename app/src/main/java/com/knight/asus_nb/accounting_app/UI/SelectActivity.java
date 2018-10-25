package com.knight.asus_nb.accounting_app.UI;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.knight.asus_nb.accounting_app.Adapter.MyRecycleAdapter;
import com.knight.asus_nb.accounting_app.Bean.RecordBean;
import com.knight.asus_nb.accounting_app.Bean.RecycleItemBean;
import com.knight.asus_nb.accounting_app.Dao.DAO;
import com.knight.asus_nb.accounting_app.R;
import com.knight.asus_nb.accounting_app.Util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.knight.asus_nb.accounting_app.Adapter.MyRecycleAdapter.OnItemClickListener;

public class SelectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecycleAdapter recycleAdapter;
    private ArrayList<RecycleItemBean> datalist;
    private EditText typeEdtext;
    private TextView textView;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private ArrayList<Map<String, Object>> arrayList;
    private boolean flag = true;//标志消费类型按钮状态
    private int recordTypeFlag = 0;//默认消费类型为支出 标志为0


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        typeEdtext = findViewById(R.id.type_edtext);
        textView = findViewById(R.id.amount_text);


        //RecycleView初始化&设置适配器&添加监听
        recyclerView = findViewById(R.id.recycle_view);
        initExpenseData();
        recycleAdapter = new MyRecycleAdapter(SelectActivity.this, datalist);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onclick(int position) {
                typeEdtext.setText(datalist.get(position).getRecycleItemText());
            }
        });
        recyclerView.setAdapter(recycleAdapter);


        //GridView 初始化
        initGridViewData();
        gridView = findViewById(R.id.mygridview);
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.mygridview_item, new String[]{"item"}, new int[]{R.id.gridview_item});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("Money".equals(textView.getText()))
                    textView.setText("");
                //判断textview里面是否已经存在小数点了，保证金额数的正确性
                if (arrayList.get(position).get("item").toString().trim().equals(".") && textView.getText().toString().contains(".")) {
                    Toast.makeText(SelectActivity.this, "输入不对了", Toast.LENGTH_SHORT).show();
                    textView.setText(textView.getText());
                } else {
                    textView.setText(textView.getText() + arrayList.get(position).get("item").toString().trim());
                }

            }
        });

    }

    //界面按钮的点击事件
    public void myclick(View view) {
        switch (view.getId()) {
            //返回主界面
            case R.id.back_btn:
                Intent backintent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(backintent);
                finish();
                break;
            //切换支出 收入类型
            case R.id.recordtype:
                btnrecordlistener();
                break;

            //删除数字按钮
            case R.id.delete:
                if ("Money".equals(textView.getText()))
                    textView.setText("");
                String str = textView.getText().toString().trim();
                if (str.length() > 0) {

                    textView.setText(str.substring(0, str.length() - 1));
                } else {
                    textView.setText("");
                }
                break;

            //确定记录信息按钮
            case R.id.done:
                btnDonelistener();
                break;

        }
    }

    //初始化RecycleView的Expense数据
    private void initExpenseData() {
        datalist = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.MyExpenseRecycleViewImg);
        String[] item = getResources().getStringArray(R.array.MyExpenseRecycleViewText);
        for (int i = 0; i < item.length; i++) {
            RecycleItemBean recycleItemBean = new RecycleItemBean();
            recycleItemBean.setRecycleItemImg(typedArray.getResourceId(i, 0));
            recycleItemBean.setRecycleItemText(item[i]);
            datalist.add(recycleItemBean);
        }

    }

    //初始化RecycleView的income数据
    private void initIncomeData() {
        datalist = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.MyIncomeRecycleViewImg);
        String[] item = getResources().getStringArray(R.array.MyIncomeRecycleViewText);
        for (int i = 0; i < item.length; i++) {
            RecycleItemBean recycleItemBean = new RecycleItemBean();
            recycleItemBean.setRecycleItemImg(typedArray.getResourceId(i, 0));
            recycleItemBean.setRecycleItemText(item[i]);
            datalist.add(recycleItemBean);
        }

    }

    //初始化GridView数据
    private void initGridViewData() {
        String[] nums = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "", "0", "."};

        arrayList = new ArrayList<>();
        for (int n = 0; n < nums.length; n++) {
            Map<String, Object> maps = new HashMap<>();
            maps.put("item", nums[n]);
            arrayList.add(maps);
        }
    }

    //打钩按钮的事件监听
    public void btnDonelistener() {
        //添加语句成功或者失败标志
        boolean resflag = false;
        Intent intent1 = getIntent();
        String date = intent1.getStringExtra("date");
        //判断是否为修改date为空说明是增加 不为空说明是修改
        if (date == null) {
            DAO dao = new DAO(SelectActivity.this);
            RecordBean rb = new RecordBean();
            //判断金额是否正确，正确则添加到数据库
            if ("Money".equals(textView.getText().toString().trim()) || "".equals(textView.getText().toString().trim()))
                Toast.makeText(SelectActivity.this, "记录失败,请输入金额", Toast.LENGTH_SHORT).show();
            else {
                rb.setType(typeEdtext.getText().toString());
                rb.setUuid(UUID.randomUUID().toString());
                rb.setAmount(Double.parseDouble(textView.getText().toString()));
                rb.setTime(DateUtil.FormateTime());
                rb.setDay(DateUtil.FormatedDay());
                rb.setRemark("");
                rb.setRecord_type(recordTypeFlag);
                resflag = dao.insertData(rb);
                if (resflag)
                    Toast.makeText(SelectActivity.this, "记录成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SelectActivity.this, "记录失败", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if (date != null) {
            DAO dao2 = new DAO(SelectActivity.this);
            RecordBean rb2 = new RecordBean();
            //判断金额是否正确，正确则添加到数据库
            if ("Money".equals(textView.getText().toString().trim()) || "".equals(textView.getText().toString().trim()))
                Toast.makeText(SelectActivity.this, "记录失败,请输入金额", Toast.LENGTH_SHORT).show();
            else {
                rb2.setType(typeEdtext.getText().toString());
                rb2.setUuid(UUID.randomUUID().toString());
                rb2.setAmount(Double.parseDouble(textView.getText().toString()));
                rb2.setTime(DateUtil.FormateTime());
                rb2.setDay(date);
                rb2.setRemark("");
                rb2.setRecord_type(recordTypeFlag);
                resflag = dao2.insertData(rb2);
                if (resflag)
                    Toast.makeText(SelectActivity.this, "记录成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SelectActivity.this, "记录失败", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);
                //date置为空，避免干扰写一次判断
                date = "";
                finish();
            }

        }
    }

    //类型切换按钮的事件监听
    public void btnrecordlistener() {
        //实现按钮的图片切换&RecycleView 视图的切换
        ImageView recordtypeImg = findViewById(R.id.recordtype);
        if (flag) {
            recordtypeImg.setImageResource(R.mipmap.earn);
            initIncomeData();
            recycleAdapter = new MyRecycleAdapter(SelectActivity.this, datalist);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onclick(int position) {
                    typeEdtext.setText(datalist.get(position).getRecycleItemText());
                }
            });
            recyclerView.setAdapter(recycleAdapter);
            //设置消费类型为收入 标志为1
            recordTypeFlag = 1;
        } else {
            recordtypeImg.setImageResource(R.mipmap.cost);
            initExpenseData();
            recycleAdapter = new MyRecycleAdapter(SelectActivity.this, datalist);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onclick(int position) {
                    typeEdtext.setText(datalist.get(position).getRecycleItemText());
                }
            });
            recyclerView.setAdapter(recycleAdapter);
            //设置消费类型为支出 标志为0
            recordTypeFlag = 0;
        }
        flag = !flag;
    }

}
