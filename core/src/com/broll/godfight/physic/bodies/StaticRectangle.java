package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;
import com.broll.godfight.physic.PhysicBody;
import com.sun.javafx.geom.Vec4f;

public class StaticRectangle extends PhysicBody {

	protected Vec4f vec;
	private float friction=1;
	protected float density=0;
	protected float restitution=0;

	public StaticRectangle(Vec4f vec){
		this.vec=vec;
		maskBits= CollisionMasks.getMask(CollisionFlag.STAGE);
		categoryBits=CollisionFlag.STAGE.getGroup();
	}
	
	
	public static void updateSprite(Sprite sprite, PhysicBody stick){
		Vector2 pos = stick.getBody().getPosition();
		float angle=stick.getBody().getAngle();
		sprite.setPosition(pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setRotation((float) Math.toDegrees(angle));
	}
	
	
	@Override
	public BodyDef createDefinition() {
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(vec.x+vec.z/2,vec.y-vec.w/2));
		return groundBodyDef;
	}

	@Override
	public void init(Body body) {
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(vec.z/2, vec.w/2);
		FixtureDef def=new FixtureDef();
		def.shape=groundBox;
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
		groundBox.dispose();
		body.setUserData(this);
	}

	@Override
	public Vec4f getBounds() {
		return new Vec4f(vec.x, vec.y, vec.x+vec.z, vec.y-vec.w);
	}
	
	@Override
	public float[] getPolygonVertices() {
		return new float[]{0,0,vec.z,0,vec.z,-vec.w,0,-vec.w};
	}

}
