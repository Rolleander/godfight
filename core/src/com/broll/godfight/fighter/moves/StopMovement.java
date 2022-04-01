package com.broll.godfight.fighter.moves;

import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.game.fighter.Fighter;

public class StopMovement extends Move {

	private boolean justOne, justVertical;

	public StopMovement() {
		justOne = false;
	}

	public StopMovement(boolean vertical) {
		justOne = true;
		justVertical = vertical;
	}

	@Override
	public void enter(Fighter fighter, boolean facingLeft) {
		Vector2 velocity = fighter.getPlayerBody().getBody().getLinearVelocity();
		float x = velocity.x;
		float y = velocity.y;
		if (justOne) {
			if (justVertical) {
				y = 0;
			} else {
				x = 0;
			}
		} else {
			x = 0;
			y = 0;
		}
		fighter.getPlayerBody().getBody().setLinearVelocity(x, y);
	}

	@Override
	public boolean update(float delta) {
		return true;
	}

}
