package com.example.imagesearch;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.ContactsContract.Contacts.Data;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "keys.db";
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHelper helper = null;
	private static final AtomicInteger usageCounter = new AtomicInteger(0);

	private Dao<KeywordDetails, String> keysDao = null;
	private RuntimeExceptionDao<KeywordDetails, String> keysRuntimeDao = null;
	private Dao<ParsedDetail, String> parseDao = null;
	private RuntimeExceptionDao<ParsedDetail, String> parseRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, KeywordDetails.class);
			TableUtils.createTable(connectionSource, ParsedDetail.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, KeywordDetails.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Dao<KeywordDetails, String> getDao() throws SQLException {
		if (keysDao == null) {
			keysDao = getDao(KeywordDetails.class);
		}
		return keysDao;
	}

	public Dao<ParsedDetail, String> getparseDao() throws SQLException {
		if (parseDao == null) {
			parseDao = getDao(KeywordDetails.class);
		}
		return parseDao;
	}

	public RuntimeExceptionDao<KeywordDetails, String> getKeysRuntimeExceptionDao() {
		if (keysRuntimeDao == null) {
			keysRuntimeDao = getRuntimeExceptionDao(KeywordDetails.class);
		}
		return keysRuntimeDao;
	}

	public RuntimeExceptionDao<ParsedDetail, String> getParseRuntimeExceptionDao() {
		if (parseRuntimeDao == null) {
			parseRuntimeDao = getRuntimeExceptionDao(ParsedDetail.class);
		}
		return parseRuntimeDao;
	}

	public static synchronized DatabaseHelper getHelper(Context context) {
		if (helper == null) {
			helper = new DatabaseHelper(context);
		}
		usageCounter.incrementAndGet();
		return helper;
	}

	@Override
	public void close() {
		if (usageCounter.decrementAndGet() == 0) {
			super.close();
			keysRuntimeDao = null;
			helper = null;
		}
	}
}
