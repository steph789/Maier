package de.gerlich.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Finisher {

	Activity activity;
	
	public static Activity maier;
	public static Activity playernames;
	public static Activity game;
	public static Activity moveslist;


	public Finisher(Activity activity) {

		this.activity = activity;
	}

	public void finishMaier() {
		
		AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(
				activity);
				//activity.getApplicationContext());

		exitDialogBuilder.setTitle("exit");

		exitDialogBuilder.setMessage("Would you like to quit this fun game?")
				.setCancelable(false)
				.setPositiveButton("Yes", new FinishConfirmed())
				.setNegativeButton("No", new Continue());

		AlertDialog alertDialog = exitDialogBuilder.create();

		alertDialog.show();
	}

	private final class Continue implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}

	}

	private final class FinishConfirmed implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			if(maier != null) maier.finish();
			if(playernames != null) playernames.finish();
			if(game != null) game.finish();
			if(moveslist != null) moveslist.finish();
			
			activity.finish();
		}
	}
}

