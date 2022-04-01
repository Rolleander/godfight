package com.broll.godfight.physic.bodies;

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
import com.broll.godfight.utils.Vector4;

public class StaticTriangle extends PhysicBody {

	private Vector4 vec;
	private boolean faceLeft;
	private float friction=1f;
	
	public StaticTriangle(Vector4 vec, boolean faceLeft){
		this.vec=vec;
		this.faceLeft=faceLeft;
		maskBits= CollisionMasks.getMask(CollisionFlag.STAGE);
		categoryBits=CollisionFlag.STAGE.getGroup();
	}
	
	@Override
	public BodyDef createDefinition() {
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(vec.x,vec.y-vec.w));
		return groundBodyDef;
	}

	@Override
	public void init(Body body) {
		PolygonShape groundBox = new PolygonShape();
		
		Vector2[] vertices=new Vector2[3];
		vertices[0]=new Vector2(0,0);
		float w=vec.z;
		float h=vec.w;
		if(!faceLeft){
			vertices[1]=new Vector2(0,h);
		}
		else
		{
			vertices[1]=new Vector2(w,h);
		}
		vertices[2]=new Vector2(w,0);
		
		
		groundBox.set(vertices);
		FixtureDef def=new FixtureDef();
		def.shape=groundBox;
		def.density=0;
		def.friction=friction;
		def.restitution=0;
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture fixture=body.createFixture(def);
		Filter filter=new Filter();
		filter.categoryBits=categoryBits;
		filter.maskBits=maskBits;
		fixture.setFilterData(filter);
	
		// Clean up after ourselves
		groundBox.dispose();
	}

	@Override
	public Vector4 getBounds() {
		return new Vector4(vec.x, vec.y, vec.x+vec.z, vec.y-vec.w);
	}
	
	@Override
	public float[] getPolygonVertices() {
		if(faceLeft){
			return new float[]{0,-vec.w,vec.z,0,vec.z,-vec.w};			
		}
		else{
			return new float[]{0,0,0,-vec.w,vec.z,-vec.w};
		}
	}

}
