package de.gerlich.maier;

import java.util.ArrayList;

import de.gerlich.util.Finisher;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

public class PlayerNamesActivity extends Activity{
	
	EditText name;
	Button save;
	TextView playerNumber;
	
	ArrayList<String> listPlayers;
	String playerName;
	short numberP;
	Boolean pressed;
	Context ctx = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_names);
		
		Bundle extras = getIntent().getExtras();
		
		numberP = extras.getShort("number");
		Log.d("PlayerNamesA bundle", "" + numberP);
		
		
		listPlayers = new ArrayList<String>();
		SaveNameListener savelistener = new SaveNameListener();
		
		pressed = false;
		playerNumber = (TextView) findViewById(R.id.playercount);
		playerNumber.setText("1");
		name = (EditText) findViewById(R.id.name);
		save = (Button) findViewById(R.id.bplayername);
		save.setOnClickListener(savelistener);

	}
	
	class SaveNameListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			playerNumber.setText("" + (listPlayers.size()+2));
			playerName = name.getText().toString();
			Log.d("neuer name", playerName);
			if(!listPlayers.contains(playerName))
				listPlayers.add(playerName);
			name.setText("");
//			pressed = true;
			
			if(listPlayers.size()==numberP) {
				for (int j = 0; j< listPlayers.size(); j++) {
					Log.d("Liste der Spieler", listPlayers.get(j));
				}
				
				Intent intent = new Intent(ctx, MaierGameActivity.class);
				intent.putStringArrayListExtra("list", listPlayers);
				startActivity(intent);
			}
		}
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Finisher.playernames = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.playernames = null;
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
