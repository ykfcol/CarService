package com.ykf.bishe.ui.adapter;

import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;

public class CityWheelAdapter extends AreaAdapter<String> {
	
	private Context context;

	public CityWheelAdapter(Context context, int provinceId){
		this.context = context;
		this.datas = Arrays.asList(context.getResources().getStringArray(provinceId));
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if(view == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.item_province,null);
			viewHolder = new ViewHolder();
			viewHolder.tv_province = (TextView) view.findViewById(R.id.tv_province);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tv_province.setText(datas.get(i).toString());
		return view;
	}

	class ViewHolder{
		TextView tv_province;
	}

/*
	@Override
	public int getItemsCount() {
		return cities == null ? 0 : cities.size();
	}

	@Override
	public String getItem(int index) {
		return index <= cities.size() - 1 ? cities.get(index) : null;
	}

	@Override
	public int getMaximumLength() {
		return 7;
	}
	
	public void setCityList(int provinceId){
		this.cities = Arrays.asList(context.getResources().getStringArray(provinceId));
	}

	@Override
	public String getCurrentId(int index) {
		return "";
	}*/
}
