package com.example.weekly_03_test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weekly_03_test.R;
import com.example.weekly_03_test.activity.CatalogueActivity;
import com.example.weekly_03_test.activity.DetailsActivity;
import com.example.weekly_03_test.bean.CatalogueBean;

import java.util.List;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder>{

    private Context context;
    private List<CatalogueBean.DataBean>dataBeans;

    public CatalogueAdapter(List<CatalogueBean.DataBean> myDataList, Context context) {
        this.dataBeans = myDataList;
        this.context = context;
    }


    @NonNull
    @Override
    public CatalogueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.catalogue_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueAdapter.ViewHolder holder, final int position) {
        holder.catalogue_tv_price.setText("￥"+dataBeans.get(position).getPrice());
        holder.catalogue_tv_salenum.setText("销量:"+dataBeans.get(position).getSalenum());
        holder.catalogue_tv_subhead.setText(dataBeans.get(position).getSubhead());
        String images = dataBeans.get(position).getImages();
        String[] split = images.split("\\|");
        RequestOptions requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        Glide.with(context).load(split[0]).apply(requestOptions).into(holder.catalogue_iv_image);

        //adapter 自定义的点击事件
        holder.click_itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("Pid",dataBeans.get(position).getPid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeans==null? 0: dataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView catalogue_iv_image;
        private TextView catalogue_tv_subhead;
        private TextView catalogue_tv_price;
        private TextView catalogue_tv_salenum;
        private View click_itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.click_itemView = itemView;
            //获取条目上的控件对象
            catalogue_iv_image = itemView.findViewById(R.id.catalogue_iv_image);
            catalogue_tv_subhead = itemView.findViewById(R.id.catalogue_tv_subhead);
            catalogue_tv_price = itemView.findViewById(R.id.catalogue_tv_price);
            catalogue_tv_salenum = itemView.findViewById(R.id.catalogue_tv_salenum);

        }
    }
}
