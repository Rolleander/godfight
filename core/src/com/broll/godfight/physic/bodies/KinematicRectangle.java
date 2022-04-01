package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.broll.godfight.utils.Vector4;

public class KinematicRectangle extends StaticRectangle {

	public KinematicRectangle(Vector4 vec) {
		super(vec);	
	}
	
	@Override
	public BodyDef createDefinition() {
		BodyDef def = super.createDefinition();
		def.type = BodyType.KinematicBody;
		return def;
	}
	
	

}
