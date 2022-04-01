package com.broll.godfight.game.fighter;

import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.fighter.BattleMove;
import com.broll.godfight.fighter.FighterPhases;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.input.Button;
import com.broll.godfight.input.InputLayout;
import com.broll.godfight.physic.ObjectJumpListener;
import com.broll.godfight.physic.bodies.PlayerBody;

public class MovementController {

	private GameEnvironment game;
	private Fighter fighter;
	private int jumpCount;
	private float lastImpulse;
	private boolean idle = false;
	private AnimationController animation;

	public MovementController(GameEnvironment game, final Fighter fighter) {
		this.fighter = fighter;
		this.game = game;
		animation = new AnimationController(game, fighter);
		fighter.getMoveset().setMoveListener(new MoveListener() {
			@Override
			public void startedMove(BattleMove move) {
				animation.stopAnimation();
			}

			@Override
			public void finishedMove(BattleMove move) {
				if (fighter.getPlayerBody().isInAir()) {
					animation.startSpriteAnimation(FighterPhases.FALL);
				} else {
					animation.startSpriteAnimation(FighterPhases.IDLE);
					idle = true;
				}
			}
		});
	}

	public void init() {
		fighter.getPlayerBody().setJumpListener(new ObjectJumpListener() {
			@Override
			public void onLanding() {
				jumpCount = 0;
				animation.startSpriteAnimation(FighterPhases.IDLE);
				idle = true;
			}

			@Override
			public void onJumping() {
				animation.startSpriteAnimation(FighterPhases.JUMP);
			}

			@Override
			public void onFalling() {
				animation.startSpriteAnimation(FighterPhases.FALL);
			}

			@Override
			public void onGettingUp() {
				animation.startSpriteAnimation(FighterPhases.ARISE);
			}

			@Override
			public void onFlung() {
				animation.startSpriteAnimation(FighterPhases.FLUNG);
			}

			@Override
			public void onStandingAgain() {
				animation.startSpriteAnimation(FighterPhases.IDLE);
			}
		});
	}

	public void throwBody(Vector2 location, Vector2 dir, float impulse) {
		jumpCount = 1;
		lastImpulse = impulse;
		fighter.getPlayerBody().throwBody(location, dir, impulse);
		fighter.getMoveset().cancelMove();
	}

	public void hitBody(Vector2 location, Vector2 dir, float impulse) {
		lastImpulse = impulse;
		float xImpulse = impulse * dir.x;
		float yImpulse = impulse * dir.y;
		fighter.getPlayerBody().applyImpulse(xImpulse, yImpulse);
		animation.startSpriteAnimation(FighterPhases.HIT);
		idle = false;
	}

	public void tryJump() {
		PlayerBody body = fighter.getPlayerBody();
		float jumpImpulse = fighter.getData().getJumpImpulse();
		int consecutiveJumps = fighter.getData().getJumpCount();
		if (!body.isFlung()) {
			if (jumpCount == 0) {
				if (body.canJump()) {
					body.jump(jumpImpulse);
					jumpCount++;
				}
			} else {
				if (body.jumpDelayDone()) {
					if (jumpCount < consecutiveJumps) {
						jumpCount++;
						body.jump(jumpImpulse);
					}
				}
			}
		}
	}

	public void update(float delta) {
		// update sprite animation
		animation.update(delta);

		// control movement
		InputLayout input = fighter.getInput();
		PlayerBody body = fighter.getPlayerBody();
		Vector2 vel = body.getBody().getLinearVelocity();
		float maxSpeed = fighter.getData().getSpeed();
		float movementImpulse = fighter.getData().getMoveImpulse();
		float airbornDamping = fighter.getData().getAirMovement();
		boolean onGround = !body.isInAir();
		boolean leftPressed = input.buttonPressed(Button.LEFT);
		boolean rightPressed = input.buttonPressed(Button.RIGHT);

		if (!onGround) {
			if (body.isFlung()) {
				airbornDamping *= 0.5f;
			}
			movementImpulse *= airbornDamping;
			// maxSpeed *= airbornDamping;
		} else {
			// on ground
			if (leftPressed || rightPressed) {
				// want move => !idle
				if (idle) {
					idle = false;
					animation.startSpriteAnimation(FighterPhases.WALK);
				}
			} else {
				// not want move => idle
				if (!idle) {
					idle = true;
					animation.startSpriteAnimation(FighterPhases.IDLE);
				}
			}
		}

		if (leftPressed) {
			if (vel.x > -maxSpeed) {
				body.applyImpulse(-movementImpulse, 0);
				if (!body.isFlung()) {
					fighter.getSprite().setFaceLeft(true);
				}
			}
		}

		if (rightPressed) {
			if (vel.x < maxSpeed) {
				body.applyImpulse(movementImpulse, 0);
				if (!body.isFlung()) {
					fighter.getSprite().setFaceLeft(false);
				}
			}
		}

		if (input.buttonPressed(Button.DOWN)) {
			body.applyImpulse(0, -movementImpulse);
		}
	}

	public float getLastImpulse() {
		return lastImpulse;
	}
}
