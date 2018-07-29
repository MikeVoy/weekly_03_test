package com.example.weekly_03_test.model;

import com.example.weekly_03_test.bean.CartBean;
import com.example.weekly_03_test.contract.CartContract;
import com.example.weekly_03_test.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CartModel implements CartContract.Model {
    @Override
    public void requestCartData(String path, final cartDataListener listener) {

        OkHttpUtils.DOGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String catalogueData = response.body().string();
                    //解析数据
                    Gson gson = new Gson();
                    CartBean cartBean = gson.fromJson(catalogueData, CartBean.class);
                    List<CartBean.DataBean> data = cartBean.getData();
                    listener.onSuccess(data);
                }
            }
        });

    }
}
