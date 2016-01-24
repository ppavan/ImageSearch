package com.example.imagesearch;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.Where;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView;

public class GalleryActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	RuntimeExceptionDao<ParsedDetail, String> parseDao;
	DatabaseHelper dbHelper2;
	ArrayList<ParsedDetail> data;
	ArrayList<ParsedDetail> data2;
	GridView gridview;
	ArrayAdapter<ParsedDetail> adapter;
	ParsedDetail parsedDetail;
	ArrayList<String> pTitles;
	String baseUrl = "https://api.500px.com/v1/photos/search?consumer_key=USERKEY&image_size=4&rpp=50";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		String search = getIntent().getExtras().getString(
				MainActivity.SEARCH_KEY);

		String URL = baseUrl + "&term=" + search;
		Log.d("url", URL);

		dbHelper2 = OpenHelperManager.getHelper(GalleryActivity.this,
				DatabaseHelper.class);
		parseDao = dbHelper2.getParseRuntimeExceptionDao();

		data2 = (ArrayList<ParsedDetail>) parseDao.queryForEq("searchterm",
				search);
		Log.d("data2", "DATA:" + data2.toString() + "DATAsize" + data2.size());
		if (data2.size() < 2) {
			new GetgallaryAsyncTask(GalleryActivity.this).execute(URL, search);
		} else {
			data = data2;
			setdata(data);
		}
	}

	public void setdata(ArrayList<ParsedDetail> photoList) {
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GalleryAdapter(this, R.layout.row_item_layout, data);
		gridview.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(GalleryActivity.this,
						DetailsActivity.class);
				ParsedDetail idObj = (ParsedDetail) data.get(position);
				intent.putExtra("photoselected", idObj);
				startActivity(intent);

				if (dbHelper2 != null) {
					dbHelper2.close();
					dbHelper2 = null;
				}
			}
		});
	}

	public void getdata(ArrayList<ParsedDetail> photoList) {
		data = new ArrayList<ParsedDetail>();
		data = photoList;

		for (ParsedDetail photorecord : data) {
			parseDao.create(new ParsedDetail(photorecord.getPhotoUrl(),
					photorecord.getPhotoName(), photorecord.getSearchterm(),
					photorecord.getOwnerName(), photorecord.getOwnerUrl(), null));
		}
		this.setdata(data);
	}

	@Override
	protected void onDestroy() {
		if (dbHelper2 != null) {
			dbHelper2.close();
			dbHelper2 = null;
		}
		super.onDestroy();
	}
}
