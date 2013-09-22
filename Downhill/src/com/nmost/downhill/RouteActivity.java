package com.nmost.downhill;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class RouteActivity extends Activity {

	ListView routeList;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.route_list_layout);
		Bundle bundle = getIntent().getExtras();
		routeList = (ListView) findViewById(R.id.route_list);
		populateListView(bundle.getString(LandingFragment.EXTRA_FROM, ""),
				bundle.getString(LandingFragment.EXTRA_FROM, ""));

	}

	private void populateListView(String from, String to) {
		// boom boom badda badda sham wow

	}

}
