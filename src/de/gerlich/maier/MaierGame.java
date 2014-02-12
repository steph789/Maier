package de.gerlich.maier;

import java.util.ArrayList;

import android.util.Log;

public class MaierGame {
	
	ArrayList<String> listPlayers;
	PlayerMove move;
	ArrayList<PlayerMove> moves;

	int score;
	boolean believed;
	boolean doubted;
	boolean ended;
	boolean lastMove;

	int dice1;
	int dice2;
	int temp;
	int activePlayerIndex;
	String activePlayerName;
	String lastPlayerName;
	String beforeLastName;
	
	public MaierGame(ArrayList<String> listPlayers) {
		this.listPlayers = listPlayers;
		moves = new ArrayList<PlayerMove>();
	}
	
	public void startMaierGame() {
		if(!ended) {
			getActivePlayerIndex();
			Log.d("Game", "activeplayerindex: " + getActivePlayerIndex());
			if(doubted) {
				//Das passiert wenn der letzte Wurf angezweifelt wird
				
				evaluateLastMove();
				
			}
			
			if(believed) {
				//Das passiert wenn nicht gezweifelt wird = würfeln
				rollTheDices();
				
			}
		}
		
	}
	
	public void setBelieved(boolean believed) {
		this.believed = believed;
	}

	public void setDoubted(boolean doubted) {
		this.doubted = doubted;
	}
	
	public int getActivePlayerIndex() {
		return activePlayerIndex;
	}
	
	public void setActivePlayerIndex(int activePlayerIndex) {
		this.activePlayerIndex = activePlayerIndex;
	}

	public int getScore() {
		return score;
	}

	public boolean isLastMove() {
		return lastMove;
	}

	public ArrayList<PlayerMove> getMoves() {
		return moves;
	}

	//Ausführung des Würfelns und Speichern des Moves
	public void rollTheDices() {
		dice1 = (int) (Math.random() * 6 + 1);
		dice2 = (int) (Math.random() * 6 + 1);
		Log.d("Würfel", "" + dice1 + " * " + dice2);

		if (dice1 < dice2) {
			temp = dice1;
			dice1 = dice2;
			dice2 = temp;
		}
		
		activePlayerName = listPlayers.get(activePlayerIndex).toString();
		score = calculateScore(concat(dice1, dice2));
		move = new PlayerMove(activePlayerName, score);
		moves.add(move);
		Log.d("Spielzug eingetragen", activePlayerName + "--" + score);
		believed = false;
	}

	public int concat(int d1, int d2) {
		int finalnum = 0;
		StringBuilder text = new StringBuilder();
		text.append(d1);
		text.append(d2);
		finalnum = Integer.parseInt(text.toString());
		Log.d("zusgesetzte zahl", "" + finalnum);
		return finalnum;
	}

	public int calculateScore(int number) {
		int myScore = 0;

		switch (number) {
		case 21: myScore = 21; break;
		case 66: myScore = 20; break;
		case 55: myScore = 19; break;
		case 44: myScore = 18; break;
		case 33: myScore = 17; break;
		case 22: myScore = 16; break;
		case 11: myScore = 15; break;
		case 65: myScore = 14; break;
		case 64: myScore = 13; break;
		case 63: myScore = 12; break;
		case 62: myScore = 11; break;
		case 61: myScore = 10; break;
		case 54: myScore = 9; break;
		case 53: myScore = 8; break;
		case 52: myScore = 7; break;
		case 51: myScore = 6; break;
		case 43: myScore = 5; break;
		case 42: myScore = 4; break;
		case 41: myScore = 3; break;
		case 32: myScore = 2; break;
		case 31: myScore = 1; break;
		default: Log.d("maiergame", "ungueltige zahl");
		}

		return myScore;
	}
	
	public boolean evaluateLastMove() {

		int indexlast = moves.size() - 1;
		int indexbefore = moves.size() - 2;
		PlayerMove lastmove = moves.get(indexlast);
		PlayerMove beforemove = moves.get(indexbefore);
		int scorelast = lastmove.getScore();
		int scorebefore = beforemove.getScore();
		
		if (scorelast > scorebefore) {
			return lastMove = true;
		}
		return lastMove;
	}

}
