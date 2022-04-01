package com.broll.godfight.game.fighter;

import com.broll.godfight.fighter.BattleMove;

public interface MoveListener {

	public void startedMove(BattleMove move);
	
	public void finishedMove(BattleMove move);
	
}
