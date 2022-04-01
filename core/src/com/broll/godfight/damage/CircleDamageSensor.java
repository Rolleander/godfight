package com.broll.godfight.damage;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.broll.godfight.physic.bodies.DamageSensor;
import com.broll.godfight.utils.Vector4;

public class CircleDamageSensor extends DamageSensor {

	private float radius;

	public CircleDamageSensor(float offsetX, float offsetY, float radius, AttackDamage damage) {
		this(offsetX, offsetY, radius, 0, false, damage);
	}

	public CircleDamageSensor(float offsetX, float offsetY, float radius, float duration, AttackDamage damage) {
		this(offsetX, offsetY, radius, duration, false, damage);
	}

	public CircleDamageSensor(float offsetX, float offsetY, float radius, float duration, boolean multiHit,
			AttackDamage damage) {
		super(damage);
		setOffsetX(offsetX);
		setOffsetY(offsetY);
		this.radius = radius;
		setDuration(duration);
		setMultipleHits(multiHit);
		this.damage = damage;
	}

	@Override
	protected void updateDamage(float delta) {
	}

	@Override
	public void init(Body body) {
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius);
		FixtureDef def = new FixtureDef();
		def.density=0;
		
		def.shape = circleShape;

		Fixture fixture = body.createFixture(def);
		//Filter filter = new Filter();
		//filter.categoryBits = CollisionFlag.DAMAGE_SENSOR.getGroup();
		//filter.maskBits = CollisionMasks.getMask(CollisionFlag.DAMAGE_SENSOR);
		//fixture.setFilterData(filter);
		fixture.setSensor(true);
		fixture.setUserData(this);
		// Clean up after ourselves
		circleShape.dispose();
	}

	@Override
	public Vector4 getBounds() {
		return null;
	}

}
