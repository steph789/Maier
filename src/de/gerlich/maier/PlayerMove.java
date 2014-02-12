package de.gerlich.maier;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerMove implements Parcelable{
	
	String name;
	int score;
	
	public PlayerMove(Parcel in) {
	    this.name = in.readString();
	    this.score = in.readInt();
	}

	public PlayerMove() {

	}
	
	public PlayerMove(String name, int score) {
		super();
		this.name = name;
		this.score = score;
	}


	public String getName() {
		return name;
	}


	public int getScore() {
		return score;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(score);
		
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	    public PlayerMove createFromParcel(Parcel in) {
	        return new PlayerMove(in);
	    }

	    public PlayerMove[] newArray(int size) {
	        return new PlayerMove[size];
	    }
	};
	
}
