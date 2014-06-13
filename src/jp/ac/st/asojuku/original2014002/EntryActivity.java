package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_activity);

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		Intent intent = this.getIntent();
		String strHitokoto = intent.getStringExtra("hitokoto");
		TextView txvHITOKOTO = (TextView)findViewById(R.id.textView1);
		txvHITOKOTO.setText(strHitokoto);
	}



}
