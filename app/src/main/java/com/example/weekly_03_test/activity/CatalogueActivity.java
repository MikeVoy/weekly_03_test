package com.example.weekly_03_test.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weekly_03_test.R;
import com.example.weekly_03_test.adapter.CatalogueAdapter;
import com.example.weekly_03_test.bean.CatalogueBean;
import com.example.weekly_03_test.contract.CatalogueContract;
import com.example.weekly_03_test.presenter.CataloguePresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity implements View.OnClickListener, CatalogueContract.View {


    private static final String TAG = CatalogueActivity.class.getSimpleName();
    private ImageView catalogue_iv_back;
    private EditText catalogue_et_name;
    private ImageView catalogue_iv_cancel;
    private ImageView catalogue_iv_switch;
    private PullToRefreshScrollView ptr_sv;
    private RecyclerView rv;
    private String productPath = "http://www.zhaoapi.cn/product/searchProducts?keywords=手机";

    private String productPage = "&page=";
    private int page = 1;
    private String productSort = "&sort=";
    private int sort = 0;
    private int i = 0;
    private CataloguePresenter cataloguePresenter;

    private boolean flag = false;
    private List<CatalogueBean.DataBean> myDataList = new ArrayList<>();
    private CatalogueAdapter catalogueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        initView();

        initOperate();
        requestData();
    }

    private void requestData() {

        //获取presenter引用
        cataloguePresenter = new CataloguePresenter(this);
        //请求数据
        cataloguePresenter.getData(productPath + productPage + page + productSort + sort);

    }

    private void initOperate() {
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ptr_sv.setMode(PullToRefreshBase.Mode.BOTH);

        ptr_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                if(page>2){
                    page--;
                }
                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        //execute the task
                        ptr_sv.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                page--;
                if(page<1){
                    page++;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr_sv.onRefreshComplete();
                    }
                },1000);

            }
        });

    }

    private void initView() {
        catalogue_iv_back = findViewById(R.id.catalogue_iv_back);
        catalogue_et_name = findViewById(R.id.catalogue_et_name);
        catalogue_iv_cancel = findViewById(R.id.catalogue_iv_cancel);
        catalogue_iv_switch = findViewById(R.id.catalogue_iv_switch);
        ptr_sv = findViewById(R.id.ptr_sv);
        rv = findViewById(R.id.rv);
        catalogue_iv_back.setOnClickListener(this);
        catalogue_iv_cancel.setOnClickListener(this);
        catalogue_iv_switch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.catalogue_iv_back:
                finish();
                break;
            case R.id.catalogue_iv_switch:

                if (flag) {
                    rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    flag = false;
                } else {
                    rv.setLayoutManager(new GridLayoutManager(this, 2));
                    flag = true;
                }

                break;
            case R.id.catalogue_iv_cancel:
                break;

        }

    }

    @Override
    public void onSuccess(List<CatalogueBean.DataBean> dataBeanList) {
        Log.i(TAG, "onSuccess: 请求到的资源" + dataBeanList.size());
        //添加进自己的集合
        if(dataBeanList.size()==0){
            return;
        }
        myDataList.addAll(dataBeanList);
        catalogueAdapter = new CatalogueAdapter(myDataList,CatalogueActivity.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rv.setAdapter(catalogueAdapter);
            }
        });


    }

    @Override
    public void onError() {

    }
}
