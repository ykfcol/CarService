package com.ykf.bishe.ui.adapter;

import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;

public class ProvinceWheelAdapter extends AreaAdapter<String> {

	private Context context;

	public ProvinceWheelAdapter(Context context){
		this.context = context;
		this.datas = Arrays.asList(context.getResources().getStringArray(R.array.province_item));
	}

	/*@Override
	public int getItemsCount() {
		return provinces == null ? 0 : provinces.size();
	}

	@Override
	public String getItem(int index) {
		return index <= provinces.size() - 1 ? provinces.get(index) : null;
	}*/

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

	/*@Override
	public int getMaximumLength() {
		return 7;
	}
	
	@Override
	public String getCurrentId(int index) {
		return "";
	}*/

}
