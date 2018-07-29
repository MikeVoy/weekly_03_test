package com.example.weekly_03_test.model;

import com.example.weekly_03_test.bean.CatalogueBean;
import com.example.weekly_03_test.contract.CatalogueContract;
import com.example.weekly_03_test.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CatalogueModel implements CatalogueContract.Model {
    @Override
    public void requestData(String path, final dataListener listener) {
        //请求数据
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
                    CatalogueBean productsBean = gson.fromJson(catalogueData, CatalogueBean.class);
                    List<CatalogueBean.DataBean> data = productsBean.getData();
                    listener.onSuccess(data);
                }

            }
        });


    }

    @Override
    public void requestCartData(String path, cartDataListener cartDataListener) {

    }
}
