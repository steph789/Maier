package de.gerlich.maier;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ScoreListAdapter extends ArrayAdapter<PlayerMove> implements OnClickListener{
	
	ArrayList<PlayerMove> moves;
	ArrayList<String> playerNames;
	int activePlayerIndex;
	private Context context;
	
	public ScoreListAdapter(Context context, int textViewResourceId, ArrayList<PlayerMove> objects, ArrayList<String> playerNames, int activePlayer) {
		super(context, textViewResourceId, objects);
		this.context = context;
		moves = objects;
		this.playerNames = playerNames;
		this.activePlayerIndex = activePlayer;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.scoringlistitem, null);
		}

		PlayerMove m = moves.get(position);

		if (m != null) {

			TextView scorerTV = (TextView) v.findViewById(R.id.scorer);
			TextView scoreTV = (TextView) v.findViewById(R.id.score);
			int red = scorerTV.getContext().getResources().getColor(R.color.darkred);
			int green = scorerTV.getContext().getResources().getColor(R.color.green);
			if (scorerTV != null){
				scorerTV.setText(m.getName());
				if (position > 0) {
					if (moves.get(position).getScore() <= moves.get(position - 1).getScore()) {
						scorerTV.setTextColor(red);
					}
					else {
						scorerTV.setTextColor(green);
					}
				}
			}
			if (scoreTV != null){
				scoreTV.setText(turnScoreToText(m.getScore()));
				if (position > 0) {
					if (moves.get(position).getScore() <= moves.get(position - 1).getScore()) {
						scoreTV.setTextColor(red);
					}
					else {
						scoreTV.setTextColor(green);
					}
				}
			}
			
		}
		
		v.setOnClickListener(this);

		return v;
	}
	
	public String turnScoreToText(int sc) {
		String myscoretxt = "";
		
		switch (sc) {
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
		return myscoretxt;
	}

	@Override
	public void onClick(View v) {
		
		AlertDialog.Builder newgameDialogBuilder = new AlertDialog.Builder(context);
				//activity.getApplicationContext());

		newgameDialogBuilder.setTitle("exit");

		newgameDialogBuilder.setMessage("Want to go on playing with this bunch?")
				.setCancelable(false)
				.setPositiveButton("Yes", new AnotherGame())
				.setNegativeButton("No", new QuitThisGame());

		AlertDialog alertDialog = newgameDialogBuilder.create();

		alertDialog.show();
		
	}
	
	private final class AnotherGame implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
						
			Intent intent = new Intent(context, MaierGameActivity.class);
			intent.putExtra("restarter", true);
			intent.putStringArrayListExtra("playerlist", playerNames);
			intent.putExtra("activeplayer", activePlayerIndex);
			((Activity) context).setResult(Activity.RESULT_OK,intent);     
			((Activity) context).finish();
		}
		
	}
	
	private final class QuitThisGame implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(context, MaierActivity.class);
			context.startActivity(intent);
		}
		
	}

}
