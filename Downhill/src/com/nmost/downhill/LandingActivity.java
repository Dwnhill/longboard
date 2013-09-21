package com.nmost.downhill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LandingActivity extends Activity {

	private Context mContext;
	private EditText mFrom, mTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.landing_activity);

		mFrom = (EditText) findViewById(R.id.input_one);
		mTo = (EditText) findViewById(R.id.input_two);
		((Button) findViewById(R.id.landing_go_button))
				.setOnClickListener(goListener);
	}

	private OnClickListener goListener = new OnClickListener() {

		@Override
		public void onClick(View b) {
			Intent intent = new Intent(mContext, RouteActivity.class);
			startActivity(intent);
		}
	};

}
