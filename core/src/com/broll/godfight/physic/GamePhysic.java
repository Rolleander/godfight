package com.broll.godfight.physic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;

public class GamePhysic {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float accumulator = 0;
	private final static float TIME_STEP = 1 / 300f;
	private final static float SPEED = 1f;
	private final static int VELOCITY_ITERATIONS = 6;
	private final static int POSITION_ITERATIONS = 2;

	private WorldContactListener worldContactListener;
	private Array<PhysicBody> garbage = new Array<PhysicBody>();

	public GamePhysic(GameEnvironment game) {
		world = new World(new Vector2(0, -10), true);
		worldContactListener = new WorldContactListener(game);
		world.setContactListener(worldContactListener);
		debugRenderer = new Box2DDebugRenderer();

	}

	private void clearGarbage() {
		if (garbage.size > 0) {
			for (PhysicBody body : garbage) {
				world.destroyBody(body.getBody());
			}
			garbage.clear();
		}
	}

	public void doPhysicStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime * SPEED;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
		// clear garbage
		worldContactListener.processObjectHits();
		clearGarbage();
	}

	public void debugRender(Camera camera) {

		debugRenderer.render(world, camera.combined);
	}

	public void addJoint(JointDef def) {
		world.createJoint(def);
	}

	public void addBody(PhysicBody body) {
		Body physic = world.createBody(body.createDefinition());
		body.initBody(physic);
	}

	public void removeBody(PhysicBody body) {
		garbage.add(body);
	}

	public boolean existsBody(PhysicBody body) {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body b : bodies) {
			if (b == body.getBody()) {
				return true;
			}
		}
		return false;
	}
}
