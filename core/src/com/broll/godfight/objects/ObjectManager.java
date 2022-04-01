package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.PhysicBody;

import java.util.Iterator;

public class ObjectManager {

	private final static float GARBAGE_CHECK_TIME = 1; // every sec
	private GameEnvironment game;
	private Array<StageObject> objects = new Array<StageObject>();
	private float garbageCheckTimer;

	public ObjectManager(GameEnvironment game) {
		this.game = game;
		garbageCheckTimer = GARBAGE_CHECK_TIME;
	}

	public void renderForeground(float delta) {
		SpriteBatch batch = game.getContainer().batch;
		for (StageObject object : objects) {
			object.renderForeground(batch,delta);
			object.update(delta);
		}
		checkGarbage(delta);
	}

	public void renderBackground(float delta) {
		SpriteBatch batch = game.getContainer().batch;
		for (StageObject object : objects) {
			object.renderBackground(batch,delta);
		}
	}

	private void checkGarbage(float delta) {
		garbageCheckTimer -= delta;
		if (garbageCheckTimer <= 0) {
			garbageCheckTimer = GARBAGE_CHECK_TIME;
			removeGarbage();
		}
	}

	/*
	 * Check for objects outside map bounds
	 */
	private void removeGarbage() {
		Iterator<StageObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			StageObject object = iterator.next();
			// check only first body
			if (object.getPhysicObjects() != null) {
				PhysicBody body = object.getPhysicObjects().get(0);
				Vector2 position = body.getBody().getPosition();
				if (game.getStageObserver().outsideBoundsObjects(position)) {
					// clean bodies and delete object
					removeObjectBodies(object);
					object.onRemove();
					iterator.remove();
				}
			}
		}
	}

	public void addObject(StageObject object) {
		objects.add(object);
		if (object.getPhysicObjects() != null) {
			for (PhysicBody body : object.getPhysicObjects()) {
				game.getPhysic().addBody(body);
			}
		}
		object.init();
	}

	public void removeObject(StageObject object) {
		objects.removeValue(object, false);
		removeObjectBodies(object);
	}

	private void removeObjectBodies(StageObject object) {
		if (object.getPhysicObjects() != null) {
			for (PhysicBody body : object.getPhysicObjects()) {
				game.getPhysic().removeBody(body);
			}
		}
	}

	public int count() {
		return objects.size;
	}

}
