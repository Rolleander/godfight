package com.broll.godfight.game;

import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.utils.Vector4;

public class StageObserver {

	private GameEnvironment game;
	private final static float HORIZONTAL_BORDER = 20;
	private final static float VERTICAL_BORDER = 15;
	private Vector4 mapBorder;

	public StageObserver(GameEnvironment game) {
		this.game = game;
	}

	public void init() {
		Vector4 bounds = game.getStage().getBounds();
		mapBorder = new Vector4(bounds.x - HORIZONTAL_BORDER, bounds.y + VERTICAL_BORDER, bounds.x + bounds.z
				+ HORIZONTAL_BORDER, bounds.y + bounds.w - VERTICAL_BORDER);
	}

	public boolean outsideBounds(Vector2 vec) {
		return vec.x < mapBorder.x || vec.x > mapBorder.z || vec.y > mapBorder.y || vec.y < mapBorder.w;
	}
	
	public boolean outsideBoundsObjects(Vector2 vec) {
		return vec.x < mapBorder.x || vec.x > mapBorder.z || vec.y < mapBorder.w;
	}
	
	

	public void fallFromStage(Fighter fighter) {
		fighter.kill();
		//teleport somewhere else 

		fighter.getPlayerBody().teleport(new Vector2(5000, 5000));

	}

	public void update(float delta) {
		for (int i = 0; i < game.getFighters().size(); i++) {
			Fighter fighter = game.getFighters().get(i);
			if (fighter.getAttributes().isOnStage()) {
				Vector2 position = fighter.getPlayerBody().getBody().getPosition();
				if (outsideBounds(position)) {
					fallFromStage(fighter);
				}
			}
		}
	}
}
