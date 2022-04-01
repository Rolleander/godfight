package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;
import com.broll.godfight.physic.PhysicBody;
import com.broll.godfight.utils.Vector4;

public class Platform extends PhysicBody {

	private Vector3 vec;
	private float friction = 1;
	private final static float HEIGHT = 0.1f;

	public Platform(Vector3 vec) {
		this.vec = vec;
		maskBits= CollisionMasks.getMask(CollisionFlag.PLATFORM);
	}

	
	@Override
	public BodyDef createDefinition() {
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.type = BodyType.KinematicBody;
		groundBodyDef.position.set(new Vector2(vec.x + vec.z / 2, vec.y - HEIGHT / 2));
		return groundBodyDef;
	}

	@Override
	public void init(Body body) {
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(vec.z / 2, HEIGHT / 2);
		FixtureDef def = new FixtureDef();
		def.shape = groundBox;
		def.density = 0;
		def.friction = friction;
		def.restitution = 0;

		// Create a fixture from our polygon shape and add it to our ground body
		Fixture fixture = body.createFixture(def);
		Filter filter = new Filter();
		filter.categoryBits = CollisionFlag.PLATFORM.getGroup();
		filter.maskBits = maskBits;
		fixture.setFilterData(filter);
		body.setUserData(this);
		// Clean up after ourselves
		groundBox.dispose();
	}

	@Override
	public Vector4 getBounds() {
		return getBounds(HEIGHT);
	}

	public Vector4 getBounds(float height) {
		return new Vector4(vec.x, vec.y, vec.x + vec.z, vec.y - height);
	}

	@Override
	public float[] getPolygonVertices() {
		return getPolygonVertices(HEIGHT);
	}

	public float[] getPolygonVertices(float height) {
		return new float[] { 0, 0, vec.z, 0, vec.z, -height, 0, -height };
	}
}
