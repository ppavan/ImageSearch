package com.example.imagesearch;

import java.sql.Date;
import java.util.ArrayList;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	RuntimeExceptionDao<ParsedDetail, String> parseDao;
	DatabaseHelper dbHelper2;
	ParsedDetail parsedDetail;
	TextView photoName, owner_name, yournote, usernote;
	ImageView photoimg, ownerimg, addnoteimg;
	ProgressDialog progressDialog;
	EditText et;
	ParsedDetail data3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		parsedDetail = (ParsedDetail) getIntent().getExtras().getSerializable(
				"photoselected");
		progressDialog = new ProgressDialog(DetailsActivity.this);
		progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);

		progressDialog.setMessage("Loading Photo ...");
		progressDialog.show();
		photoName = (TextView) findViewById(R.id.textView1);
		photoimg = (ImageView) findViewById(R.id.imageView1);
		ownerimg = (ImageView) findViewById(R.id.imageView2);
		owner_name = (TextView) findViewById(R.id.textView2);

		yournote = (TextView) findViewById(R.id.textView3);
		usernote = (TextView) findViewById(R.id.textView4);
		addnoteimg = (ImageView) findViewById(R.id.imageView3);
		et = (EditText) findViewById(R.id.editText1);
		photoName.setText(parsedDetail.getPhotoName());
		owner_name.setText(parsedDetail.getOwnerName());

		dbHelper2 = OpenHelperManager.getHelper(DetailsActivity.this,
				DatabaseHelper.class);
		parseDao = dbHelper2.getParseRuntimeExceptionDao();

		data3 = parseDao.queryForId(parsedDetail.getPhotoUrl());
		if (data3.getUserNote() == null) {
			yournote.setVisibility(View.GONE);
			usernote.setVisibility(View.GONE);

			addnoteimg.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (et.getText().toString() != null) {
						parseDao.update(new ParsedDetail(data3.getPhotoUrl(),
								data3.getPhotoName(), data3.getSearchterm(),
								data3.getOwnerName(), data3.getOwnerUrl(), et
										.getText().toString()));

						et.setVisibility(View.GONE);
						addnoteimg.setVisibility(View.GONE);
						yournote.setVisibility(View.VISIBLE);
						usernote.setVisibility(View.VISIBLE);
						yournote.setText("Your Note");
						usernote.setText(et.getText().toString());
					}
					return false;
				}
			});
		} else {
			et.setVisibility(View.GONE);
			addnoteimg.setVisibility(View.GONE);
			yournote.setVisibility(View.VISIBLE);
			usernote.setVisibility(View.VISIBLE);
			yournote.setText("Your Note");
			usernote.setText(data3.getUserNote());
		}

		// Add from DB
		yournote.setText("Your note: ");
		if (isConnectedOnline()) {
			if (parsedDetail.getPhotoUrl() != null) {
				Picasso.with(DetailsActivity.this)
						.load(parsedDetail.getPhotoUrl()).into(photoimg);
			} else {
				photoimg.setImageResource(R.drawable.photo_not_found);
			}
			if (parsedDetail.getOwnerUrl() != null) {
				Picasso.with(DetailsActivity.this)
						.load(parsedDetail.getOwnerUrl()).into(ownerimg);
			} else {
				ownerimg.setImageResource(R.drawable.user_not_found);
			}
		} else {
			photoimg.setImageResource(R.drawable.photo_not_found);
			ownerimg.setImageResource(R.drawable.user_not_found);
			Toast.makeText(DetailsActivity.this, "No Network Connection",
					Toast.LENGTH_LONG).show();
		}
		progressDialog.dismiss();
	}

	private boolean isConnectedOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
