package com.broll.godfight.game.fighter;

import com.broll.godfight.fighter.FighterPhases;
import com.broll.godfight.fighter.SpriteAnimation;
import com.broll.godfight.game.GameEnvironment;

import java.util.Stack;

public class AnimationController {
	private Stack<SpriteAnimation> animationStack = new Stack<SpriteAnimation>();
	private SpriteAnimation currentAnimation;
	private int currentAnimationStep;
	private GameEnvironment game;
	private Fighter fighter;
	
	public AnimationController(GameEnvironment game, Fighter fighter) {
		this.game=game;
		this.fighter=fighter;
	}
	
	public void stopAnimation()
	{
		currentAnimation = null;
		animationStack.clear();
	}
	
	private void checkForNextAnimation() {
		if (animationStack.isEmpty()) {
			if (currentAnimation != null && currentAnimation.isLoop()) {
				// push same animation again
				animationStack.push(currentAnimation);
			} else {
				currentAnimation = null;
				return;
			}
		}

		currentAnimation = animationStack.pop();
		if (currentAnimation != null) {
			currentAnimationStep = 0;
			currentAnimation.buildAnimation(fighter);
			currentAnimation.getMoveset().get(0).enterMove(game, fighter, fighter.getSprite().isFacingLeft());
		}
	}

	public void startSpriteAnimation(FighterPhases phase) {
		animationStack.push(fighter.getData().getSpriteAnimations().get(phase));
		if (currentAnimation != null) {
			if (currentAnimation.isSkippable()) {
				currentAnimation = null;
			}
		}
		if (currentAnimation == null) {
			checkForNextAnimation();
		}
	}
	
	public void update(float delta){
		if (currentAnimation != null) {
			if (currentAnimation.getMoveset().get(currentAnimationStep).update(delta)) {
				currentAnimationStep++;
				if (currentAnimationStep < currentAnimation.getMoveset().size()) {
					currentAnimation.getMoveset().get(currentAnimationStep)
							.enterMove(game, fighter, fighter.getSprite().isFacingLeft());
				} else {
					checkForNextAnimation();
				}
			}
		}
	}
}
