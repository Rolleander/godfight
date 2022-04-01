package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.fighter.Fighter;

public class Wait extends Move {

	private float waiting;
	private float waitSeconds;

	public Wait(float waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	@Override
	public void enter(Fighter fighter, boolean facingLeft) {
		waiting = 0;
	}

	@Override
	public boolean update(float delta) {
		waiting += delta;
		return waiting >= waitSeconds;
	}

}
