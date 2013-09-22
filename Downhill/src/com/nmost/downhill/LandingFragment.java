package com.nmost.downhill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nmost.services.NetworkService.NetworkResponseListener;

public class LandingFragment extends Fragment {

	public static final String EXTRA_FROM = "EXTRA_FROM";
	public static final String EXTRA_TO = "EXTRA_TO";
	public static final String URL = "http://dwnhll.herokuapp.com";
	private View mRoot;
	private MainFragmentActivity mFragmentActivity;
	private EditText mFrom, mTo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.landing_activity, container,
				false);
		mFragmentActivity = (MainFragmentActivity) getActivity();
		mRoot = root;
		mFrom = (EditText) mRoot.findViewById(R.id.input_one);
		mTo = (EditText) mRoot.findViewById(R.id.input_two);

		((Button) mRoot.findViewById(R.id.landing_go_button))
				.setOnClickListener(goListener());

		return root;

	}

	private OnClickListener goListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDatas();
			}
		};
	}

	private void getDatas() {
		String startLoc = mFrom.getText().toString().replace(" ", "+");
		String endLoc = mTo.getText().toString().replace(" ", "+");

		mFragmentActivity.getNetwork().get(
				URL + "/route/" + startLoc + "/" + endLoc,
				new NetworkResponseListener() {
					@Override
					public void onSuccess(InputStream data) {
						JSONObject fullObject = null;
						try {
							fullObject = parseStream(data);
							if (fullObject != null) {
								String error = fullObject.optString("status");
								if (error.equals("200")) {
									// move on by
								} else if (error.equals("300")) {
									JSONArray fromLocs = fullObject
											.optJSONArray("startLocationMatches");
									JSONArray toLocs = fullObject
											.optJSONArray("endLocationMatches");

									buildDialogForMultiple(fromLocs, toLocs);
								}
							}
						} catch (IOException e) {

						} catch (JSONException e) {

						}

					}

					@Override
					public void onError(Exception error) {
						error.printStackTrace();

					}
				});

	}

	private void buildDialogForMultiple(final JSONArray fromLocs,
			final JSONArray toLocs) {
		List<String> fromList = new ArrayList<String>();
		List<String> toList = new ArrayList<String>();

		try {
			if (fromLocs.length() > 1) {
				if (fromLocs != null) {
					for (int i = 0; i < fromLocs.length(); i++) {
						fromList.add(fromLocs.getJSONObject(i).optString(
								"address"));
					}
				}
			}

			if (toLocs.length() > 1) {

				if (toLocs != null) {
					for (int i = 0; i < toLocs.length(); i++) {
						toList.add(toLocs.getJSONObject(i).optString("address"));
					}
				}
			} else {
				mFragmentActivity.setTo(toLocs.optJSONObject(0));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		final List<String> finalFromList = fromList;
		final List<String> finalToList = toList;

		mFragmentActivity.runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog fromDialog;
				final boolean toNeeded = (finalToList != null && finalToList
						.size() > 1);

				if (finalToList != null && !toNeeded) {
					mFragmentActivity.setTo(toLocs.optJSONObject(0));

				}

				if (finalFromList != null && finalFromList.size() > 1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							mFragmentActivity);
					builder.setTitle("Which one? (From)").setItems(
							finalFromList
									.toArray(new CharSequence[finalFromList
											.size()]),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									mFragmentActivity.setFrom(fromLocs
											.optJSONObject(which));
									if (toNeeded) {
										showTo(finalToList, toLocs);
									}
								}
							});

					fromDialog = builder.create();
					fromDialog.show();
				} else {
					if (finalFromList != null) {
						mFragmentActivity.setFrom(fromLocs.optJSONObject(0));
					}
					if (toNeeded) {
						showTo(finalToList, toLocs);
					}
				}

			}
		});

	}

	private void showTo(final List<String> finalToList, final JSONArray toLocs) {
		AlertDialog toDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(mFragmentActivity);
		builder.setTitle("Which one? (From)").setItems(
				finalToList.toArray(new CharSequence[finalToList.size()]),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mFragmentActivity.setTo(toLocs.optJSONObject(0));
					}
				});

		toDialog = builder.create();
		toDialog.show();

	}

	private JSONObject parseStream(InputStream in) throws IOException,
			JSONException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(
				in, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);
		return new JSONObject(responseStrBuilder.toString());
	}

}
