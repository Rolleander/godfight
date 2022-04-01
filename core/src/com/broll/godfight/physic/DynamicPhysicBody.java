package com.broll.godfight.physic;

import com.badlogic.gdx.math.Vector2;

public abstract class DynamicPhysicBody extends PhysicBody {

	public void applyImpulse(float impulseX, float impulseY, float pointX, float pointY){
		body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, true);
	}
	
	public void applyImpulse(float impulseX, float impulseY){
		Vector2 pos = body.getPosition();
		applyImpulse(impulseX, impulseY, pos.x, pos.y);
	}
}
