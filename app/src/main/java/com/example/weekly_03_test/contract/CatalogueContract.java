package com.example.weekly_03_test.contract;

import com.example.weekly_03_test.bean.CatalogueBean;

import java.util.List;

public interface CatalogueContract {
    interface Model {

        interface dataListener{
            void onSuccess(List<CatalogueBean.DataBean> dataBeanList);
            void  onError();
        }
        void requestData(String path,dataListener listener);

        interface cartDataListener{

            void onSuccessful();
            void onError();
        }

        void requestCartData(String path,cartDataListener cartDataListener);

    }

    interface View {

        void onSuccess(List<CatalogueBean.DataBean> dataBeanList);
        void onError();
    }

    interface Presenter {

        void getData(String path);
        void onDestory();

    }
}
