package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = null;
		switch(v.getId()) {//メンテボタン
			case R.id.bt1:
				intent = new Intent(MainActivity.this, ListActivity.class);
				startActivity(intent);
				break;

			case R.id.bt3://チェックボタン
				String strHitokoto = helper.selectRandomHitokoto(sdb);
				intent = new Intent(MainActivity.this, EntryActivity.class);
				intent.putExtra("hitokoto", strHitokoto);
				startActivity(intent);
				break;

			case R.id.bt2: //登録ボタン
				EditText etv = (EditText)findViewById(R.id.edt1);
				String inputMsg = etv.getText().toString();

				if(inputMsg != null && !inputMsg.isEmpty()) {
					helper.insertHitokoto(sdb, inputMsg);
				}
				etv.setText("");
				break;

		}


	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ

		Button bt1 = (Button)findViewById(R.id.bt1); //メンテボタン
		bt1.setOnClickListener(this);

		Button bt2 = (Button)findViewById(R.id.bt2); //登録ボタン
		bt2.setOnClickListener(this);

		Button bt3 = (Button)findViewById(R.id.bt3); //一言チェック
		bt3.setOnClickListener(this);
		super.onResume();

		if(sdb == null) {
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}

		try {
			sdb = helper.getWritableDatabase();
		} catch(SQLiteException e) {
			return;//異常終了
		}
	}



}
