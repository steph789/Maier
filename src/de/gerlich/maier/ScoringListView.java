package de.gerlich.maier;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import de.gerlich.util.Finisher;

public class ScoringListView extends ListActivity{
	
	ArrayList<Parcelable> movesParcel;
	ArrayList<PlayerMove> moves = new ArrayList<PlayerMove>();
	ArrayList<String> playerNames;
	int activePlayerIndex;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		
		movesParcel = extras.getParcelableArrayList("moves");
		for (int i = 0; i < movesParcel.size(); i++) {
			moves.add((PlayerMove) movesParcel.get(i));
		}
		playerNames = extras.getStringArrayList("list");
		activePlayerIndex = extras.getInt("activeplayer");
		
		ScoreListAdapter adapter = new ScoreListAdapter(this, R.layout.scoringlistitem, moves, playerNames, activePlayerIndex);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Finisher.moveslist = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.moveslist = null;
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
