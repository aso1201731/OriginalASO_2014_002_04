package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
//import android.widget.Toast;

public class ListActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{

	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;
	int selectedID = -1;
	int lastPosition = -1; //行番号を保持する変数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);


	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.selectedID!=-1) {
			parent.getChildAt(this.lastPosition).setBackgroundColor(0);
		}
		view.setBackgroundColor(android.graphics.Color.RED);

		SQLiteCursor cursor = (SQLiteCursor)parent.getItemAtPosition(position);

		this.selectedID = cursor.getInt(cursor.getColumnIndex("id"));
		this.lastPosition = position;

	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()) {
		case R.id.button2:
			if(this.selectedID != -1) {
				this.deleteFromHitokoto(this.selectedID);
				ListView lstHitokoto = (ListView)findViewById(R.id.listView1);
				this.setDBValuetoList(lstHitokoto);
				this.selectedID = -1;
				this.lastPosition = -1;
			} else {
				Toast.makeText(ListActivity.this, "削除する行を選択しろ", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button1:
			finish();
		break;
		}

	}

	private void deleteFromHitokoto(int id) {
		// TODO 自動生成されたメソッド・スタブ
		if(sdb == null) {
			helper  = new MySQLiteOpenHelper(getApplicationContext());
		} try {
			sdb = helper.getWritableDatabase();
		} catch(SQLiteException e) {
			Log.e("ERROR", e.toString());
		}
		this.helper.deleteHitokoto(sdb, id);

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		Button btnDelete = (Button)findViewById(R.id.button2);
		Button btnMainte_Back = (Button)findViewById(R.id.button1);
		ListView lstHitokoto = (ListView)findViewById(R.id.listView1);

		btnDelete.setOnClickListener(this);
		btnMainte_Back.setOnClickListener(this);

		lstHitokoto.setOnItemClickListener(this);

		this.setDBValuetoList(lstHitokoto);

	}

	private void setDBValuetoList(ListView lstHitokoto) {
		// TODO 自動生成されたメソッド・スタブ
		SQLiteCursor cursor = null;
		if(sdb == null) {
			helper  = new MySQLiteOpenHelper(getApplicationContext());
		}

		try {
			sdb = helper.getWritableDatabase();
		} catch (SQLiteException e) {
			Log.e("ERROR", e.toString()); //異常終了
		}

		cursor = this.helper.selectHitokotoList(sdb);

		int db_layout = android.R.layout.simple_list_item_activated_1;
		String[] from = {"phrase"};
		int[] to = new int[]{android.R.id.text1};

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, db_layout, cursor, from, to, 0);

		lstHitokoto.setAdapter(adapter);
	}





}
