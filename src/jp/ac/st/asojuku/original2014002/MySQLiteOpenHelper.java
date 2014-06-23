package jp.ac.st.asojuku.original2014002;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	/**
	 * @param content 呼び出しコンテクスト
	 * @param name 利用DB名
	 * @param factory カーソルファクトリー
	 * @param version DBバージョン
	 */

	public MySQLiteOpenHelper(Context context) {
		super(context,"20140021201731.sqlite3", null, 1);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("CREATE TABLE IF NOT EXISTS Hitokoto(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, phrase TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("DROP TABLE Hitokoto;");
		onCreate(db);
	}

	public void insertHitokoto(SQLiteDatabase db, String inputMsg) {
		String sqlstr = "INSERT INTO Hitokoto (phrase) VALUES(' "+ inputMsg + " ');";
			try {
				db.beginTransaction();
				db.execSQL(sqlstr);
				db.setTransactionSuccessful();
			} catch (SQLException e) {
				Log.e("ERROR", e.toString());
			}finally {
				db.endTransaction();
			}
		return;
	}

	public String selectRandomHitokoto(SQLiteDatabase db) {
		String rtString = null;

		String sqlstr = "SELECT id, phrase FROM Hitokoto ORDER BY RANDOM();";
			try {
				SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sqlstr, null);
				if(cursor.getCount()!=0) {
					cursor.moveToFirst();
					rtString = cursor.getString(1);
				}
				cursor.close();

			} catch (SQLException e) {
				Log.e("ERROR", e.toString());
			}finally {

			}
		return rtString;
	}

	/**
	 * Hitokotoテーブルからデータをすべて取得
	 * @param SQLiteDatabase SELECTアクセスするDBのインスタンス変数
	 * @return 取得したデータの塊の表（導出表）のレコードをポイントするカーソル
	 */

	public SQLiteCursor selectHitokotoList(SQLiteDatabase db) {
		SQLiteCursor cursor = null;

		String sqlstr = " SELECT id, phrase FROM Hitokoto ORDER BY id; ";
		try {
			cursor = (SQLiteCursor)db.rawQuery(sqlstr, null);
			if(cursor.getCount()!=0) {
				cursor.moveToFirst();
			}
		} catch (SQLException e) {
			Log.e("ERROR", e.toString());
		} finally {

		}
		return cursor;
	}


	public void deleteHitokoto(SQLiteDatabase db, int id) {
		String sqlstr = " DELETE FROM Hitokoto WHERE id =" + id + ";";
		try {
			db.beginTransaction();
			db.execSQL(sqlstr);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("ERROR", e.toString());
		} finally {
			db.endTransaction();
		}
	}



}
