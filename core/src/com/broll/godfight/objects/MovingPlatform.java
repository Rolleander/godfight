package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.bodies.Platform;

public class MovingPlatform extends StageObject {

	private Platform platform;
	private Vector2[] waypoints;
	private int currentWaypoint;
	private float speed;
	private Sprite sprite;
	private Vector2 size;
	private boolean forward = true;
	private boolean moveUp, moveRight;

	public MovingPlatform(GameEnvironment game, Vector2 size, float speed, Vector2[] waypoints) {
		super(game);
		this.speed = speed;
		this.size = size;
		this.waypoints = waypoints;
		currentWaypoint = 0;
		Vector2 startPos = waypoints[0];
		platform = new Platform(new Vector3(startPos.x, startPos.y, size.x));
		sprite = new Sprite(game.getResource().getTexture("rock2.png"));
		sprite.setSize(size.x, size.y);
		addBody(platform);
	}

	@Override
	public void init() {
		moveToWaypoint();
	}

	private void moveToWaypoint() {
		Vector2 position = platform.getBody().getPosition();
		Vector2 target = waypoints[currentWaypoint];
		float xdist = target.x - position.x;
		float ydist = target.y - position.y;
		float angle = (float) Math.atan2(ydist, xdist);
		float xSpeed = (float) (Math.cos(angle) * speed);
		float ySpeed = (float) (Math.sin(angle) * speed);
		platform.getBody().setLinearVelocity(xSpeed, ySpeed);
		moveRight = xdist > 0;
		moveUp = ydist > 0;
	}

	private void checkWaypoint(Vector2 position) {
		Vector2 target = waypoints[currentWaypoint];
		float xdist = target.x - position.x;
		float ydist = target.y - position.y;
		boolean right = xdist > 0;
		boolean up = ydist > 0;
		if ((xdist == 0 || moveRight ^ right) && (ydist == 0 || moveUp ^ up)) {
			// reached waypoint
			//correct body
			platform.getBody().setTransform(target, 0);
			nextWaypoint();
			moveToWaypoint();
		}
	}

	private void nextWaypoint() {
		if (forward) {
			currentWaypoint++;
			if (currentWaypoint == waypoints.length - 1) {
				forward = false;
			}
		} else {
			currentWaypoint--;
			if (currentWaypoint == 0) {
				forward = true;
			}
		}
	}

	@Override
	public void renderBackground(SpriteBatch batch, float delta) {
	}

	@Override
	public void renderForeground(SpriteBatch batch, float delta) {
		Vector2 position = platform.getBody().getPosition();
		float x = position.x - size.x / 2;
		float y = position.y - size.y + 0.2f;
		sprite.setPosition(x, y);
		sprite.draw(batch);
		checkWaypoint(position);
	}

}
