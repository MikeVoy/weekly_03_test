package com.example.weekly_03_test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.weekly_03_test.R;
import com.example.weekly_03_test.adapter.CartAdapter;
import com.example.weekly_03_test.adapter.CatalogueAdapter;
import com.example.weekly_03_test.bean.CartBean;
import com.example.weekly_03_test.bean.CatalogueBean;
import com.example.weekly_03_test.contract.CartContract;
import com.example.weekly_03_test.contract.CatalogueContract;
import com.example.weekly_03_test.presenter.CartPresenter;
import com.example.weekly_03_test.presenter.CataloguePresenter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private static final String TAG = CartActivity.class.getSimpleName();
    private ExpandableListView cart_evl;
    private TextView cart_tv_checkout;
    private CheckBox cart_rb_checkall;
    private CataloguePresenter cataloguePresenter;

    private String api ="http://www.zhaoapi.cn/product/getCarts?uid=71";
    private CartPresenter cartPresenter;
    private List<CartBean.DataBean>myDataList = new ArrayList<>();
    private CartAdapter cartAdapter;

    private boolean ischecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        inttOperate();
    }

    private void inttOperate() {
         cartPresenter = new CartPresenter(this);
         Log.i(TAG, "inttOperate: 在购物车请求的网址"+api);
         cartPresenter.requestCartData(api);
         cart_rb_checkall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(ischecked){
                     cartAdapter.setCheckAll(ischecked);
                     ischecked = false;
                      cart_rb_checkall.setChecked(ischecked);
                 }else {

                     cartAdapter.setCheckAll(ischecked);
                     ischecked = true;
                     cart_rb_checkall.setChecked(ischecked);
                 }

             }
         });

    }

    private void initView() {
        cart_evl = findViewById(R.id.cart_elv);
        cart_tv_checkout  = findViewById(R.id.cart_tv_checkout);
        cart_rb_checkall = findViewById(R.id.cart_rb_checkall);
    }


    @Override
    public void onSuccess(List<CartBean.DataBean> dataBeans) {
        Log.i(TAG, "onSuccess: 购物车请求到的资源" + dataBeans.size());
        //添加进自己的集合
        if(dataBeans.size()==0){
            return;
        }
        myDataList.addAll(dataBeans);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cartAdapter = new CartAdapter(myDataList, CartActivity.this, new CartAdapter.Callback() {
                    @Override
                    public void cartAdatapterListener(int countsMoney, int countsNum) {
                         cart_rb_checkall.setText("已选("+countsNum+")");
                         cart_tv_checkout.setText("小计"+countsMoney);
                    }
                });
                cart_evl.setAdapter(cartAdapter);
                int count = cart_evl.getCount();
                for (int i = 0; i < count; i++) {
                    cart_evl.expandGroup(i);
                }
            }
        });
    }

    @Override
    public void onError() {

    }
}
