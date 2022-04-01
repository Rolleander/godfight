package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;

public abstract class Move {

	protected Fighter fighter;
	protected boolean facingLeft;
	protected GameEnvironment game;

	public void enterMove(GameEnvironment game, Fighter fighter, boolean facingLeft) {
		this.fighter = fighter;
		this.facingLeft=facingLeft;
		this.game=game;
		enter(fighter,facingLeft);
	}

	protected abstract void enter(Fighter fighter, boolean facingLeft);

	public abstract boolean update(float delta);
}
