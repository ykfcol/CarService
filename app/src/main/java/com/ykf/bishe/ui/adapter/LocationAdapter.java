package com.ykf.bishe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Location;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ykf on 16/5/12.
 */
public class LocationAdapter extends RecyclerView.Adapter {

    List<Location> locations;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
        Location location = locations.get(position);
        locationViewHolder.tv_repair_address.setText(location.getName());
        locationViewHolder.tv_location.setText(location.getDistrict());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = locationViewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(locationViewHolder.itemView, pos);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return locations == null ? 0 : locations.size();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_repair_name)
        TextView tv_repair_address;
        @Bind(R.id.tv_location)
        TextView tv_location;

        public LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
