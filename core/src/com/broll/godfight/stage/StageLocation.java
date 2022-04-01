package com.broll.godfight.stage;

import com.badlogic.gdx.math.Vector2;

public class StageLocation {

	private Vector2 location;
	private boolean faceLeft;
	
	public StageLocation(Vector2 location, boolean faceLeft) {
		this.location=location;
		this.faceLeft=faceLeft;
	}

	public Vector2 getLocation() {
		return location;
	}
	
	public boolean isFaceLeft() {
		return faceLeft;
	}
}
