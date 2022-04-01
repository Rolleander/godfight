package com.broll.godfight.input;

import com.broll.godfight.fighter.BattleMove;
import com.broll.godfight.game.fighter.Fighter;

import java.util.List;

public class MoveInputSensor {

	private final static float SWEEP_TIME = 0.1f;
	private List<BattleMove> moves;
	private Fighter fighter;
	private MoveTrigger trigger;
	private float waitingTime;

	public MoveInputSensor(Fighter fighter, MoveTrigger trigger) {
		this.moves = fighter.getData().getMoves();
		this.fighter = fighter;
		this.trigger = trigger;
	}

	private void triggerMove(int nr) {
		trigger.triggerMove(nr);
		// reset all inputs
		for (int i = 0; i < moves.size(); i++) {
			moves.get(i).getButtonCombination().reset();
		}
	}

	public void update(float time) {
		// check for button activation
		waitingTime += time;
		InputLayout layout = fighter.getInput();
		for (int i = 0; i < moves.size(); i++) {
			// update inputs
			if (moves.get(i).getButtonCombination().checkCombination(time, layout)) {
				// check periodic 
				if (waitingTime >= SWEEP_TIME) {
					if (canUseMove(i)) {
						
						triggerMove(i);

						break;
					}
				}
			}
		}
		if (waitingTime >= SWEEP_TIME) {
			waitingTime = 0;
		}
	}

	private boolean canUseMove(int nr) {
		BattleMove move = moves.get(nr);
		if (move.isCombo()) {
			// combo can be used right now
			if (move.isActive()) {
				return true;
			}
		} else {
			// everywhere
			if (move.isInAir() && move.isOnGround()) {
				return true;
			} else {
				// ground move
				if (move.isOnGround() && fighter.getPlayerBody().onGround()) {
					return true;
				}
				// air move
				else if (move.isInAir() && fighter.getPlayerBody().isInAir()) {
					return true;
				}
			}
		}
		return false;
	}

}
