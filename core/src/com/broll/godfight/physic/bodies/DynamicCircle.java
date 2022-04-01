package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;

public class DynamicCircle extends StaticCircle {

	public DynamicCircle(Vector2 position, float radius, float density, float restitution) {
		this(position, radius, density, restitution, false);
	}

	public DynamicCircle(Vector2 position, float radius, float density, float restitution, boolean debry) {
		super(position, radius);
		this.density = density;
		this.restitution = restitution;
		if (debry) {
			categoryBits = CollisionFlag.DEBRY.getGroup();
			maskBits = CollisionMasks.getMask(CollisionFlag.DEBRY);
		} else {
			categoryBits = CollisionFlag.OBJECT.getGroup();
			maskBits = CollisionMasks.getMask(CollisionFlag.OBJECT);
		}
	}

	@Override
	public BodyDef createDefinition() {
		BodyDef def = super.createDefinition();
		def.type = BodyType.DynamicBody;
		return def;
	}

}
