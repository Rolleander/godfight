package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.fighter.Fighter;

public class Impulse extends Move {

	private float xImpulse, yImpulse;

	public Impulse(float xImpulse, float yImpulse) {
		this.xImpulse = xImpulse;
		this.yImpulse = yImpulse;
	}

	@Override
	public void enter(Fighter fighter, boolean facingLeft) {
		//fighter.getPlayerBody().getBody().setLinearVelocity(0, 0);
		float x = xImpulse;
		if (facingLeft) {
			x *= -1;
		}
		fighter.getPlayerBody().applyImpulse(x, yImpulse);
	}

	@Override
	public boolean update(float delta) {
		return true;
	}

}
