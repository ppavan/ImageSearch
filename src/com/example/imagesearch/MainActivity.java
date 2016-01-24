package com.example.imagesearch;

import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	EditText searchTerm;
	public static final String SEARCH_KEY = "Search";
	RuntimeExceptionDao<KeywordDetails, String> keysDao;
	String[] historyArray;
	DatabaseHelper dbHelper1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView historyImg = (ImageView) findViewById(R.id.imageView1);
		searchTerm = (EditText) findViewById(R.id.editText1);
		Button submit = (Button) findViewById(R.id.button1);
		historyImg.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				keysDao = getHelper().getKeysRuntimeExceptionDao();
				List<KeywordDetails> historyTerms = keysDao.queryForAll();
				Collections.sort(historyTerms);
				Log.d("history", historyTerms.toString());
				historyArray = new String[historyTerms.size()];
				for (int i = 0; i < historyTerms.size(); i++) {
					historyArray[i] = historyTerms.get(i).getKeyword()
							.toString();
					Log.d("historyarray", historyArray[i]);
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Search History");
				builder.setItems(historyArray,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.d("his", "Inside Alert");
								searchTerm.setText(historyArray[which]);
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

				return false;
			}
		});

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String searchterm = null;
				if (searchTerm.getText() != null) {
					searchterm = searchTerm.getText().toString();
				}
				if (searchterm != null) {
					dbHelper1 = OpenHelperManager.getHelper(MainActivity.this,
							DatabaseHelper.class);
					keysDao = dbHelper1.getKeysRuntimeExceptionDao();

					if (!keysDao.idExists(searchterm)) {
						keysDao.create(new KeywordDetails(searchterm, new Date(
								System.currentTimeMillis())));
					} else {
						keysDao.update(new KeywordDetails(searchterm, new Date(
								System.currentTimeMillis())));
					}
					Intent intent = new Intent(MainActivity.this,
							GalleryActivity.class);
					intent.putExtra(SEARCH_KEY, searchterm);

					if (dbHelper1 != null) {
						dbHelper1.close();
						dbHelper1 = null;
					}

					startActivity(intent);
					// finish();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (dbHelper1 != null) {
			dbHelper1.close();
			dbHelper1 = null;
		}
		super.onDestroy();
	}
}
