
package com.example.imagesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GetgallaryAsyncTask extends
		AsyncTask<String, Void, ArrayList<ParsedDetail>> {
	GalleryActivity activity;
	ProgressDialog progressDialog;

	public GetgallaryAsyncTask(GalleryActivity activity) {
		this.activity = activity;
	}

	@Override
	protected ArrayList<ParsedDetail> doInBackground(String... params) {
		URL url;
		try {
			url = new URL(params[0]);
			String search = params[1];
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();

				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				return gallaryUtil.galleryJSONParser.parseGallery(
						sb.toString(), search);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMax(100);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Data ...");
		progressDialog.show();
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(ArrayList<ParsedDetail> result) {
		if (result != null) {
			activity.getdata(result);
		} else {
			Toast.makeText(activity, "No value Found!", Toast.LENGTH_LONG)
					.show();
		}
		progressDialog.dismiss();
		super.onPostExecute(result);
	}
}
