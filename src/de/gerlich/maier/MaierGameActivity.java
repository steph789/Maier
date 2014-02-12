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
import android.widget.ImageButton;
import android.widget.TextView;

public class MaierGameActivity extends Activity {
	ArrayList<String> listPlayers;
	PlayerMove move;
	ArrayList<PlayerMove> moves;
	MaierGame game;

	int score;
	boolean ended;
	Button doubt;
	Button believe;
	Button showScore;
	Button next;
	Button end;
	ImageButton imgbt;

	final int[] imgSizeIds = new int[]{ R.drawable.m31, R.drawable.m32,
							R.drawable.m41, R.drawable.m42, R.drawable.m43,
							R.drawable.m51, R.drawable.m52, R.drawable.m53,
							R.drawable.m54, R.drawable.m61, R.drawable.m62,
							R.drawable.m63, R.drawable.m64, R.drawable.m65, R.drawable.m11,
							R.drawable.m22, R.drawable.m33, R.drawable.m44, R.drawable.m55,
							R.drawable.m66, R.drawable.m21 };
	int activePlayerIndex;
	String activePlayerName;
	String lastPlayerName;
	String beforeLastName;
	Boolean restarted = false;

	Context context = this;
	final int REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Bundle extras = getIntent().getExtras();
		if(extras.containsKey("restarter")) {
			restarted = extras.getBoolean("restarter");
		}
		if(extras.containsKey("list"))
			listPlayers = extras.getStringArrayList("list");
		
		if(!restarted) 
			moves = new ArrayList<PlayerMove>();
				
		game = new MaierGame(listPlayers);

		EndListener endlistener = new EndListener();
		end = (Button) findViewById(R.id.end);
		end.setOnClickListener(endlistener);

		DiceListener dicelistener = new DiceListener();
		DoubtListener doubtlistener = new DoubtListener();
		HideListener hidelistener = new HideListener();
		NextListener nextlistener = new NextListener();

		believe = (Button) findViewById(R.id.rollTheDice);
		doubt = (Button) findViewById(R.id.doubtIt);
		next = (Button) findViewById(R.id.next);
		//if(moves.isEmpty())
			doubt.setVisibility(View.GONE);
		
		believe.setOnClickListener(dicelistener);
		doubt.setOnClickListener(doubtlistener);
		next.setOnClickListener(nextlistener);

		showScore = (Button) findViewById(R.id.scorehide);
		imgbt = (ImageButton) findViewById(R.id.yourDices);
		showScore.setOnClickListener(hidelistener);
		imgbt.setOnClickListener(hidelistener);

		TextView playerName = (TextView) findViewById(R.id.namePlayerCalled);
		playerName.setText(listPlayers.get(0).toString());

		activePlayerName = listPlayers.get(0).toString();

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Finisher.game = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.game = null;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1 && resultCode == RESULT_OK) {
			listPlayers = data.getStringArrayListExtra("playerlist");
			activePlayerIndex = data.getIntExtra("activeplayer", 0);
			moves.clear();
			Log.d("onActivityResult", "Liste gelöscht");
			next.setVisibility(View.GONE);
		}
	}

	////////////////////////// -- LISTENER -- ///////////////////////////////////////////////////////

	class DoubtListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			game.setBelieved(false);
			game.setDoubted(true);
			game.startMaierGame();

			String right = "Cheers to you - the last move was correct.";
			String wrong = "What a liar! The last move was incorrect. View the course of this game";
			
			next.setVisibility(View.VISIBLE);

			if (!game.isLastMove()) {
				next.setText(wrong);
			} else {
				next.setText(right);
			}

		}
	}

	class DiceListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			//next.setVisibility(View.GONE);
			//doubt.setVisibility(View.GONE);
			//believe.setVisibility(View.GONE);
			game.setBelieved(true);
			game.setDoubted(false);
			game.setActivePlayerIndex(activePlayerIndex);
			game.startMaierGame();
			
			score = game.getScore();
			String myscoretxt = "";
			
			switch (score) {
			case 21: myscoretxt = "MAIER!";	break;
			case 20: myscoretxt = "6 doubles"; break;
			case 19: myscoretxt = "5 doubles"; break;
			case 18: myscoretxt = "4 doubles"; break;
			case 17: myscoretxt = "3 doubles"; break;
			case 16: myscoretxt = "2 doubles"; break;
			case 15: myscoretxt = "1 doubles"; break;
			case 14: myscoretxt = "65"; break;
			case 13: myscoretxt = "64"; break;
			case 12: myscoretxt = "63"; break;
			case 11: myscoretxt = "62"; break;
			case 10: myscoretxt = "61"; break;
			case 9: myscoretxt = "54"; break;
			case 8: myscoretxt = "53"; break;
			case 7: myscoretxt = "52"; break;
			case 6: myscoretxt = "51"; break;
			case 5: myscoretxt = "43"; break;
			case 4: myscoretxt = "42"; break;
			case 3: myscoretxt = "41"; break;
			case 2: myscoretxt = "32"; break;
			case 1: myscoretxt = "31"; break;
			default: Log.d("maiergame", "ungueltige zahl");
			}
			
			showScore.setVisibility(View.VISIBLE);
			showScore.setText(myscoretxt + " Click to hide");
			imgbt.setVisibility(View.VISIBLE);
			imgbt.setImageResource(imgSizeIds[score - 1]);

			activePlayerIndex++;

			if (activePlayerIndex == listPlayers.size() )
				activePlayerIndex = 0;

		}
	}
	
	class HideListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			showScore.setVisibility(View.GONE);
			imgbt.setVisibility(View.GONE);
			if(doubt.getVisibility()==View.GONE)
				doubt.setVisibility(View.VISIBLE);
			
			TextView playerName = (TextView) findViewById(R.id.namePlayerCalled);
			playerName.setText(listPlayers.get(activePlayerIndex).toString());
			
			//doubt.setVisibility(View.VISIBLE);
			//believe.setVisibility(View.VISIBLE);
			
		}
		
	}
	
	class NextListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			moves = game.getMoves();
			Bundle extras = new Bundle();
			extras.putStringArrayList("list", listPlayers);
			extras.putParcelableArrayList("moves", moves);
			extras.putInt("activeplayer", activePlayerIndex);
			Intent intent = new Intent(context, ScoringListView.class);
			intent.putExtras(extras);
			startActivityForResult(intent, REQUEST_CODE);
			
		}
		
	}

	class EndListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ended = true;
			Intent intent = new Intent(context, MaierActivity.class);
			startActivity(intent);
		}
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
