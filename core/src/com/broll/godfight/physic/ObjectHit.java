package com.broll.godfight.physic;

import com.broll.godfight.physic.bodies.DamageSensor;

public class ObjectHit {

	private PhysicBody body;
	private DamageSensor sensor;
	
	public ObjectHit(PhysicBody body, DamageSensor sensor) {
		super();
		this.body = body;
		this.sensor = sensor;
	}

	public PhysicBody getBody() {
		return body;
	}

	public DamageSensor getSensor() {
		return sensor;
	}
	
	
}
