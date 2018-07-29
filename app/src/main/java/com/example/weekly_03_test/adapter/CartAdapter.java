package com.example.weekly_03_test.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weekly_03_test.R;
import com.example.weekly_03_test.bean.CartBean;

import java.util.List;

public class CartAdapter extends BaseExpandableListAdapter {

    private List<CartBean.DataBean>dataBeans;
    private Context context;
    private Callback callback;
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    //钱数
    private int countsMoney;
    //选中个数
    private int countsNum;


    public CartAdapter(List<CartBean.DataBean> myDataList, Context cartActivity,Callback callback) {

        this.context = cartActivity;
        this.dataBeans = myDataList;
        this.callback = callback;
    }


    //全选全不选
    public void setCheckAll(boolean isCheckd){

        //根据外界传来的Boolean值来给集合中的每一个单选框赋上值
        for (CartBean.DataBean li : dataBeans){
            List<CartBean.DataBean.ListBean> list = li.getList();
            for (CartBean.DataBean.ListBean list1 : list){
                if(isCheckd){
                    list1.setSelected(0);
                }else {
                    list1.setSelected(1);
                }
            }
        }
         notifyDataSetChanged();
    }
    //计算 此方法由外部调用
    public void getCountMoneyAndNum(){
         countsMoney = 0;
         countsNum = 0;
         if(dataBeans==null){
             return;
         }
         //外部先遍历商家接口,在遍历商家接口的同时,遍历商家中的商品集合
         for (CartBean.DataBean listBean: dataBeans){

             List<CartBean.DataBean.ListBean> li = listBean.getList();
             for (CartBean.DataBean.ListBean l : li){
                 if(l.getSelected()==1){
                    countsMoney+= (l.getNum()*l.getPrice());

                    countsNum += l.getNum();
                 }
             }
         }
    }
    //重写适配器刷新事件
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //每次刷新调用获取价格和数量的方法
        getCountMoneyAndNum();

        callback.cartAdatapterListener(countsMoney,countsNum);

    }


    @Override
    public int getGroupCount() {
        return dataBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataBeans.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataBeans.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition+1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolder_group holder_group = null;
        if(convertView==null){
            View view = LayoutInflater.from(context).inflate(R.layout.cart_group_item, null, false);
            convertView = view;
            holder_group = new ViewHolder_group(view);

            convertView.setTag(holder_group);

        }else {
               holder_group = (ViewHolder_group) convertView.getTag();
        }
        holder_group.cart_group_tname.setText(dataBeans.get(groupPosition).getSellerName());

        return convertView;
    }



    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, null, false);
            convertView = view;
            holder = new ViewHolder(view);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取子类集合
        final CartBean.DataBean.ListBean listBean = dataBeans.get(groupPosition).getList().get(childPosition);
        holder.cart_item_bargainPrice.setText(listBean.getBargainPrice()+"");
        holder.cart_item_price.setText(listBean.getPrice()+"");
        //给price添加线
        holder.cart_item_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cart_item_title.setText(listBean.getTitle());
        //购物车商品数量
        holder.cart_item_count.setText(listBean.getNum()+"");

         //网址
        String images = listBean.getImages();
        String[] split = images.split("\\|");
        RequestOptions requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        Glide.with(context).load(split[0]).apply(requestOptions).into(holder.cart_item_imagge);

        //数量加一的监听
        holder.cart_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // listBean.getNum();
                int num = dataBeans.get(groupPosition).getList().get(childPosition).getNum();
                num++;
                listBean.setNum(num);
                notifyDataSetChanged();
            }
        });
        //数量减一的监听
        holder.cart_item_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = dataBeans.get(groupPosition).getList().get(childPosition).getNum();
                if(num>1){
                    num--;
                }else {
                    num=0;
                }
                dataBeans.get(groupPosition).getList().get(childPosition).setNum(num);
                notifyDataSetChanged();

            }
        });

        //判断选中
        if(listBean.getSelected()==0){
            holder.cart_item_rb.setChecked(false);
        }else if(listBean.getSelected()==1){
                 holder.cart_item_rb.setChecked(true);
        }
        //checkBox的监听
        holder.cart_item_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = dataBeans.get(groupPosition).getList().get(childPosition).getSelected();
                if(selected==0){
                    dataBeans.get(groupPosition).getList().get(childPosition).setSelected(1);
                }else if(selected==1){
                    dataBeans.get(groupPosition).getList().get(childPosition).setSelected(0);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class  ViewHolder{

        private CheckBox cart_item_rb;
        private ImageView cart_item_imagge;
        private TextView cart_item_title;
        private TextView cart_item_sub;
        private TextView cart_item_add;
        private TextView cart_item_count;
        private TextView cart_item_bargainPrice;
        private TextView cart_item_price;

        public ViewHolder(View view) {
            cart_item_rb =   view.findViewById(R.id.cart_item_rb);
            cart_item_imagge =   view.findViewById(R.id.cart_item_img);
            cart_item_title =   view.findViewById(R.id.cart_item_title);
            cart_item_sub =   view.findViewById(R.id.cart_item_sub);
            cart_item_add =   view.findViewById(R.id.cart_item_add);
            cart_item_count =   view.findViewById(R.id.cart_item_count);
            cart_item_price =   view.findViewById(R.id.cart_item_price);
            cart_item_bargainPrice =   view.findViewById(R.id.cart_item_bargainPrice);
        }
    }

    class ViewHolder_group{
        private TextView cart_group_tname;
        public ViewHolder_group(View view) {
            cart_group_tname = view.findViewById(R.id.cart_group_tame);
        }
    }

    //声明接口
    public interface Callback{
        void cartAdatapterListener(int countsMoney,int countsNum);
    }


}
