package insomniac.insomniacv112;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class StartMenuActivity extends Activity implements
		OnClickListener {
	Button btnNew;
	Button btnLoad;
	Button btnSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_menu);
		btnNew = (Button) findViewById(R.id.btn_new_account);
		btnLoad = (Button) findViewById(R.id.btn_load_account);
		btnSettings = (Button) findViewById(R.id.btn_settings);
		btnNew.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btn_new_account) {
			Intent intent = new Intent(this, NewAccountActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.btn_load_account) {
			Intent intent = new Intent(this, LoadAccountActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.btn_settings) {
			//TODO
		}
		
	}

}
