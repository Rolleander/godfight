package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.PhysicBody;

public abstract class StageObject {

	protected GameEnvironment game;
	protected Array<PhysicBody> bodies = new Array<PhysicBody>();
	private float removeTimer;
	private boolean removing=false;
	
	public StageObject(GameEnvironment game) {
		this.game = game;
	}

	protected void addBody(PhysicBody body) {
		bodies.add(body);
	}

	public void onRemove() {
	}

	public abstract void init();

	protected void removeIn(float seconds){
		if(!removing)
		{
		removing=true;
		removeTimer=seconds;
		}
	}
	
	public void update(float delta){
		removeTimer-=delta;
		if(removeTimer<=0&&removing){
			game.getObjects().removeObject(this);
		}
	}
	
	public abstract void renderBackground(SpriteBatch batch, float delta);

	public abstract void renderForeground(SpriteBatch batch, float delta);

	public Array<PhysicBody> getPhysicObjects() {
		return bodies;
	}
}
