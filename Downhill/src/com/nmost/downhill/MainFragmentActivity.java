package com.nmost.downhill;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

import com.nmost.datatypes.Location;
import com.nmost.services.NetworkService;

public class MainFragmentActivity extends FragmentActivity {

	private boolean mIsBound = false;
	protected NetworkService mBoundService;
	private Location mFrom, mTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_container);

		mConnection = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mBoundService = ((NetworkService.NetworkBinder) service)
						.getService();
				mIsBound = true;

			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mBoundService = null;
			}

		};

		doBindService();
	}

	private ServiceConnection mConnection;

	void doBindService() {
		bindService(new Intent(getApplicationContext(), NetworkService.class),
				mConnection, Context.BIND_AUTO_CREATE);
	}

	public NetworkService getNetwork() {
		return mBoundService;
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(mConnection);
			mIsBound = false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

	public Location getFrom() {
		return mFrom;
	}

	public void setFrom(JSONObject from) {
		JSONObject coordinates = from.optJSONObject("coords");
		mFrom = new Location(from.optString("address"), Double.valueOf(coordinates
				.optString("lng")), Double.valueOf(coordinates
				.optString("lat")));
	}

	public Location getTo() {
		return mTo;
	}

	public void setTo(JSONObject to) {
		JSONObject coordinates = to.optJSONObject("coords");
		mTo = new Location(to.optString("address"), Double.valueOf(coordinates
				.optString("lng")), Double.valueOf(coordinates
				.optString("lat")));
	}
}
