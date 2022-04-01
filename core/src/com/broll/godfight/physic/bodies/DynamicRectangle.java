package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;
import com.sun.javafx.geom.Vec4f;

public class DynamicRectangle extends StaticRectangle{

	public DynamicRectangle(Vec4f vec,float density, float restitution) {
		this(vec,density,restitution,false);
	}
	
	public DynamicRectangle(Vec4f vec,float density, float restitution, boolean debry) {
		super(vec);	
		this.density=density;
		this.restitution=restitution;
		if(debry){
			categoryBits=CollisionFlag.DEBRY.getGroup();
			maskBits= CollisionMasks.getMask(CollisionFlag.DEBRY);
		}else{
			categoryBits=CollisionFlag.OBJECT.getGroup();			
			maskBits= CollisionMasks.getMask(CollisionFlag.OBJECT);
		}
	}
	
	@Override
	public BodyDef createDefinition() {
		BodyDef def = super.createDefinition();
		def.type = BodyType.DynamicBody;
		return def;
	}

}
