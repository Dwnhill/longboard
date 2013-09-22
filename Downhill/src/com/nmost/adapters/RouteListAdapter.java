package com.nmost.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nmost.datatypes.Location;
import com.nmost.downhill.R;

public class RouteListAdapter extends ArrayAdapter<Location> {

	private Context mContext;
	private ArrayList<Location> routes = new ArrayList<Location>();

	public RouteListAdapter(Context context, int resource,
			int textViewResourceId, List<Location> objects) {
		super(context, resource, textViewResourceId, objects);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = ((Activity) mContext).getLayoutInflater().inflate(
					R.layout.route_list_item, parent);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.route_list_item_name);
			holder.dist = (TextView) convertView
					.findViewById(R.id.route_list_item_distance);
			holder.y = (TextView) convertView
					.findViewById(R.id.route_list_item_deltay);

		} else {

		}
		return super.getView(position, convertView, parent);
	}

}

class ViewHolder {
	public TextView name;
	public TextView dist;
	public TextView y;
}
