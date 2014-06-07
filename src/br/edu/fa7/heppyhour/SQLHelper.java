package br.edu.fa7.heppyhour;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

	private String sql;
	private String tableName;
	
	public SQLHelper(Context context, String name, int version, String sql, String tableName) {
		super(context, name, null, version);
		this.sql = sql;
		this.tableName = tableName;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + tableName);
		onCreate(db);
	}
	
	

}
