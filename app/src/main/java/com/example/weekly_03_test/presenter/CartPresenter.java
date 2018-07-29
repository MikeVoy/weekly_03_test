package com.example.weekly_03_test.presenter;

import com.example.weekly_03_test.activity.CartActivity;
import com.example.weekly_03_test.bean.CartBean;
import com.example.weekly_03_test.contract.CartContract;
import com.example.weekly_03_test.model.CartModel;

import java.util.List;

public class CartPresenter implements CartContract.Presenter {

    private CartContract.View  view;
    private CartModel cartModel;

    public CartPresenter(CartContract.View cartView) {

        this.view = cartView;
        this.cartModel = new CartModel();
    }

    @Override
    public void requestCartData(String path) {
           cartModel.requestCartData(path, new CartContract.Model.cartDataListener() {
               @Override
               public void onSuccess(List<CartBean.DataBean> dataBeans) {
                       view.onSuccess(dataBeans);
               }

               @Override
               public void onError() {

               }
           });
    }
}
