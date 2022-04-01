package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.BodyHitListener;
import com.broll.godfight.physic.bodies.DamageSensor;
import com.broll.godfight.physic.bodies.DynamicRectangle;
import com.broll.godfight.physic.bodies.StaticRectangle;
import com.broll.godfight.utils.Vector4;

public class WoodBox extends StageObject {

	private final static float SIZE = 1;
	private final static float DENSITY = 50;
	private final static float RESTITUTION = 0.5f;

	private Sprite sprite;
	private float percent;
	private DynamicRectangle body;

	public WoodBox(GameEnvironment game, Vector2 location) {
		super(game);
		body = new DynamicRectangle(new Vector4(location.x - SIZE / 2, location.y - SIZE / 2, SIZE, SIZE), DENSITY,
				RESTITUTION);
		sprite = new Sprite(game.getResource().getTexture("box.jpg"));
		sprite.setSize(SIZE, SIZE);
		percent = 20;
		addBody(body);
	}

	
	private void killBox(){
		//spawn debry
		int count=4;
		float impulse=30;
		float x=body.getBody().getPosition().x;
		float y=body.getBody().getPosition().y;
		
		float height=SIZE/(float)count;
		float width=SIZE;
		float angle=body.getBody().getAngle();
		for(int i=0; i<count; i++){		
			Vector2 pos=new Vector2(x, y);
			Vector2 size=new Vector2(width, height);
			WoodDebry debry=new WoodDebry(game, pos, size);
			game.getObjects().addObject(debry);
			Body debryBody = debry.getPhysicObjects().get(0).getBody();
			debryBody.setTransform(pos, angle);
			//punch debris away
			float ra=(float) (Math.random()*Math.PI*2);
			float impulseX=(float) (Math.cos(ra)*impulse);
			float impulseY=(float) (Math.sin(ra)*impulse);
			Vector2 boxPos=new Vector2((float) (x+Math.random()*width-Math.random()*width), (float) (y+Math.random()*height-Math.random()*height));
			debryBody.applyLinearImpulse(new Vector2(impulseX, impulseY), boxPos, true);
			//move pos
			x+=Math.cos(angle)*height;
			y+=Math.sin(angle)*height;		
		}
		//kill box
		game.getObjects().removeObject(this);
	}
	
	@Override
	public void init() {
		body.setHitListener(new BodyHitListener() {
			@Override
			public void hit(DamageSensor sensor) {
				percent -= sensor.getDamage().getDamage();
				if (percent <= 0) {
					killBox();
				}
			}
		});
	}

	@Override
	public void renderForeground(SpriteBatch batch, float delta) {
		StaticRectangle.updateSprite(sprite, body);
		sprite.draw(batch);
	}

	@Override
	public void renderBackground(SpriteBatch batch, float delta) {
	}

}
