package com.broll.godfight.physic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.broll.godfight.physic.bodies.DamageSensor;
import com.sun.javafx.geom.Vec4f;

public abstract class PhysicBody {

	protected Body body;
	protected short maskBits,categoryBits;
	private BodyHitListener hitListener;
	
	public abstract BodyDef createDefinition();
	
	public abstract void init(Body body);
	
	public abstract Vec4f getBounds();
	
	public void initBody(Body body){
		this.body=body;
		init(body);
	}
	
	public Body getBody() {
		return body;
	}

	public float[] getPolygonVertices() {
		return null;
	}

	public void noCollision() {
		maskBits=0;
	}
	
	public void setHitListener(BodyHitListener hitListener) {
		this.hitListener = hitListener;
	}
	
	public void onHit(DamageSensor sensor){
		if(hitListener!=null){
			hitListener.hit(sensor);
		}
	}
}
