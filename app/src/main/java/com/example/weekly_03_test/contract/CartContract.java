package com.example.weekly_03_test.contract;

import com.example.weekly_03_test.bean.CartBean;

import java.util.List;

public interface CartContract {
    interface Model {

        interface cartDataListener{
            void onSuccess(List<CartBean.DataBean>dataBeans);
            void onError();
        }
        void requestCartData(String path,cartDataListener listener);
    }

    interface View {
        void onSuccess(List<CartBean.DataBean>dataBeans);
        void onError();
    }

    interface Presenter {
        void requestCartData(String path);
    }
}
