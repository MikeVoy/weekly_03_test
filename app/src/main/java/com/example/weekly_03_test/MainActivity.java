package com.example.weekly_03_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.example.weekly_03_test.activity.CatalogueActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_search;
    private EditText et_search_content;
    private TextView tv_cacel;
    private ImageView iv_delete_record;
    private AutoFlowLayout afl_layout;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        iv_search = findViewById(R.id.iv_search);
        et_search_content = findViewById(R.id.et_search_content);
        tv_cacel = findViewById(R.id.tv_cacel);
        iv_delete_record = findViewById(R.id.iv_delete_record);
        afl_layout = findViewById(R.id.afl_layout);
        iv_search.setOnClickListener(this);
        tv_cacel.setOnClickListener(this);
        iv_delete_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_search:
                //获取值
                String s = et_search_content.getText().toString();
                if(TextUtils.isEmpty(s)){
                    return;
                }else {
                    stringList.add(s);
                    putListToAdapter();

                    Intent intent = new Intent(MainActivity.this, CatalogueActivity.class);
                    intent.putExtra(s,"PrdocutName");
                    startActivity(intent);
                }
                break;
            case R.id.tv_cacel:
                //清空输入框
                et_search_content.getText().clear();
                break;
            case R.id.iv_delete_record:
                stringList.clear();
                afl_layout.removeAllViews();
                break;
        }

    }

    private void putListToAdapter() {
        afl_layout.setAdapter(new FlowAdapter(stringList) {
            private View view;
            @Override
            public View getView(int i) {

                if(stringList.size()>0){
                   view = View.inflate(MainActivity.this, R.layout.afl_layout_item, null);
                   TextView afl_tv_item = view.findViewById(R.id.afl_tv_item);
                   afl_tv_item.setText(stringList.get(i));
                   //清一下集合,防止一直叠加
                    stringList.clear();
                }


                return view;
            }
        });

    }
}
