package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.fighter.Fighter;

public class Jump extends Move {

	
	public Jump() {
	
	}

	@Override
	public void enter(Fighter fighter, boolean facingLeft) {
		//fighter.getPlayerBody().getBody().setLinearVelocity(0, 0);
		fighter.getMovement().tryJump();
	}

	@Override
	public boolean update(float delta) {
		return true;
	}

}
