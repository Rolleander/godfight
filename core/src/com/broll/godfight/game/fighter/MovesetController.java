package com.broll.godfight.game.fighter;

import com.broll.godfight.fighter.BattleMove;
import com.broll.godfight.fighter.moves.Move;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.input.MoveInputSensor;
import com.broll.godfight.input.MoveTrigger;

import java.util.List;

public class MovesetController {

	private Fighter fighter;
	private final static int NO_MOVE = -1;

	private List<BattleMove> moves;
	private int moveRunning = NO_MOVE;
	private int moveAction = 0;
	private MoveListener moveListener;
	private boolean facingLeft;
	private float combinationTime;
	private MoveInputSensor inputSensor;
	private GameEnvironment game;
	
	public MovesetController(GameEnvironment game, Fighter fighter) {
		this.fighter = fighter;
		this.game=game;
		moves = fighter.getData().getMoves();
		inputSensor = new MoveInputSensor(fighter, new MoveTrigger() {
			@Override
			public void triggerMove(int nr) {
				startMove(nr);
			}
		});
	}

	public void setMoveListener(MoveListener moveListener) {
		this.moveListener = moveListener;
	}

	public boolean moveRunning() {
		return moveRunning > NO_MOVE;
	}

	public void cancelMove() {
		moveRunning = NO_MOVE;
		setCombosState(false);
		combinationTime = 0;
	}

	private void startMove(int moveId) {
		moveRunning = moveId;
		moveAction = 0;
		facingLeft = fighter.getSprite().isFacingLeft();
		BattleMove move = moves.get(moveRunning);
		move.buildMove(fighter);
		if (move.isCombo()) {
			move.setActive(false);
		}
		moves.get(moveRunning).getMoveset().get(0).enterMove(game,fighter, facingLeft);

		moveListener.startedMove(moves.get(moveRunning));
	}

	private void finishedMove() {
		BattleMove move = moves.get(moveRunning);
		moveListener.finishedMove(move);
		moveRunning = NO_MOVE;
		// activate combos for short time
		if (move.getCombos().size() > 0) {
			for (BattleMove combo : move.getCombos()) {
				for (int i = 0; i < moves.size(); i++) {
					BattleMove comboMove = moves.get(i);
					if (combo == comboMove) {
						// activate
						comboMove.setActive(true);
					}
				}
			}
			combinationTime = move.getComboTime();
		}
		for (int i = 0; i < moves.size(); i++) {
			moves.get(i).getButtonCombination().reset();
		}
	}

	private void setCombosState(boolean active) {
		for (int i = 0; i < moves.size(); i++) {
			BattleMove move = moves.get(i);
			if (move.isCombo()) {
				move.setActive(active);
			}
		}
	}

	public void update(float delta) {
	//	float realDelta = ((float) delta + 1000) / 1000f;
	//	float time = realDelta / ((float) Gdx.graphics.getFramesPerSecond());
		if (moveRunning()) {
			// update move
			BattleMove move = moves.get(moveRunning);
			Move action = move.getMoveset().get(moveAction);
			if (action.update(delta)) {
				// trigger next
				moveAction++;
				if (moveAction >= move.getMoveset().size()) {
					// finished move
					finishedMove();
				} else {
					// enter next
					move.getMoveset().get(moveAction).enterMove(game,fighter, facingLeft);
				}
			}
		} else {
			//update sensors
			inputSensor.update(delta);
		}
		if (combinationTime > 0) {
			combinationTime -= delta;
			if (combinationTime <= 0) {
				setCombosState(false);
			}
		}
	}

}
