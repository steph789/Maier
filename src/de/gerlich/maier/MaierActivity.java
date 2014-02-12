package de.gerlich.maier;

import de.gerlich.util.Finisher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MaierActivity extends Activity implements OnClickListener{
	
	EditText numberOfPlayers;
	Button ok;
	short numberP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maier);
		
		numberOfPlayers = (EditText) findViewById(R.id.numberplayers);
		ok = (Button) findViewById(R.id.bplayers);
		
		ok.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		numberP = Short.parseShort(numberOfPlayers.getText().toString());
		Log.d("MaierActivity No of players", "" + numberP);
		Intent intent = new Intent(this, PlayerNamesActivity.class);
		intent.putExtra("number", numberP);
		startActivity(intent);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		Finisher.maier = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.maier = null;
	}
	
	
	
	//////////////////////////////////// --- MENU --- /////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.maier, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Finisher f = new Finisher(this);
    	f.finishMaier();
		return true;
	}

}
