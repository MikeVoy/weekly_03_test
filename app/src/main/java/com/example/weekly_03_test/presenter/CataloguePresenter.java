package com.example.weekly_03_test.presenter;

import android.util.Log;

import com.example.weekly_03_test.bean.CatalogueBean;
import com.example.weekly_03_test.contract.CatalogueContract;
import com.example.weekly_03_test.model.CatalogueModel;

import java.util.List;

public class CataloguePresenter implements CatalogueContract.Presenter {
    private static final String TAG = CataloguePresenter.class.getSimpleName();

    private CatalogueContract.View  catalogueView;
    private CatalogueModel catalogueModel;

    public CataloguePresenter(CatalogueContract.View view) {
        this.catalogueView = view;
        this.catalogueModel = new CatalogueModel();
    }

    @Override
    public void getData(String path) {

        Log.i(TAG, "getData: CataloguePresente接受的网址"+path);
        catalogueModel.requestData(path, new CatalogueContract.Model.dataListener() {
            @Override
            public void onSuccess(List<CatalogueBean.DataBean> dataBeanList) {
                catalogueView.onSuccess(dataBeanList);
            }

            @Override
            public void onError() {
            }
        });
    }
    @Override
    public void onDestory() {
        if(catalogueView!=null){
            catalogueView = null;
        }
    }
}
