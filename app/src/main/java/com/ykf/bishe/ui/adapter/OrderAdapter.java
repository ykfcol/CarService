package com.ykf.bishe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.model.entity.element.Weather;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ykf on 16/5/11.
 */
public class OrderAdapter extends RecyclerView.Adapter{

    List<Order> orders;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        Order order = orders.get(position);
        orderViewHolder.tv_repair_name.setText(order.getArea());
        orderViewHolder.tv_repair_address.setText(order.getAddress());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = orderViewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(orderViewHolder.itemView, pos);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }
    public void setOrders(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_repair_name) TextView tv_repair_name;
        @Bind(R.id.tv_repair_address) TextView tv_repair_address;
        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
