package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.fighter.Fighter;

public class SetFrame extends Move {

	private int frame;
	
	public SetFrame(int frame) {
		this.frame=frame;
	}
	
	@Override
	public void enter(Fighter fighter, boolean facingLeft) {
		fighter.getSprite().setFrame(frame);
	}

	@Override
	public boolean update(float delta) {
		return true;
	}

}
