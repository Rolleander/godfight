package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;
import com.broll.godfight.physic.PhysicBody;
import com.broll.godfight.utils.Vector4;

public class StaticCircle extends PhysicBody{

	protected Vector2 position;
	protected float radius;
	private float friction=1;
	protected float density=0;
	protected float restitution=0;
	
	public StaticCircle(Vector2 position, float radius){
		this.position=position;
		this.radius=radius;
		maskBits= CollisionMasks.getMask(CollisionFlag.STAGE);
		categoryBits=CollisionFlag.STAGE.getGroup();
	}
	

	
	@Override
	public BodyDef createDefinition() {
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(position.x+radius,position.y-radius));
		return groundBodyDef;
	}

	@Override
	public void init(Body body) {
		CircleShape shape=new CircleShape();
		shape.setRadius(radius);
	
		FixtureDef def=new FixtureDef();
		def.shape=shape;
		def.density=density;
		def.friction=friction;
		def.restitution=restitution;
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture fixture=body.createFixture(def);
		Filter filter=new Filter();
		filter.categoryBits=categoryBits;
		filter.maskBits=maskBits;
		fixture.setFilterData(filter);
		// Clean up after ourselves
		shape.dispose();
		body.setUserData(this);
	}

	@Override
	public Vector4 getBounds() {
		return new Vector4(position.x	, position.y, position.x+radius*2, position.y-radius*2);
	}
	
	@Override
	public float[] getPolygonVertices() {
		return null;
	}

}
