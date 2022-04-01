package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.bodies.DynamicCircle;
import com.broll.godfight.physic.bodies.StaticRectangle;

public class Football extends StageObject{

	private final static float RADIUS=0.25f;
	private final static float DENSITY = 40;
	private final static float RESTITUTION = 0.7f;
	private DynamicCircle ball;
	private Sprite sprite;
	
	public Football(GameEnvironment game, Vector2 position) {
		super(game);
		
		ball=new DynamicCircle(position, RADIUS, DENSITY, RESTITUTION);
		addBody(ball);
		sprite=new Sprite(game.getResource().getTexture("ball.png"));
		sprite.setSize(RADIUS*2,RADIUS*2);
	}

	@Override
	public void init() {
	}

	@Override
	public void renderBackground(SpriteBatch batch, float delta) {
	}

	@Override
	public void renderForeground(SpriteBatch batch, float delta) {
		StaticRectangle.updateSprite(sprite, ball);
		sprite.draw(batch);
	}

}
