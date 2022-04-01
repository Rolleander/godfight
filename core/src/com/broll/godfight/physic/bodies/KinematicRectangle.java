package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sun.javafx.geom.Vec4f;

public class KinematicRectangle extends StaticRectangle {

	public KinematicRectangle(Vec4f vec) {
		super(vec);	
	}
	
	@Override
	public BodyDef createDefinition() {
		BodyDef def = super.createDefinition();
		def.type = BodyType.KinematicBody;
		return def;
	}
	
	

}
