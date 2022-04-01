package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.bodies.DynamicRectangle;
import com.broll.godfight.physic.bodies.StaticRectangle;
import com.sun.javafx.geom.Vec4f;

public class WoodDebry extends StageObject {

	private final static float DENSITY = 20;
	private final static float RESTITUTION = 0.6f;
	private final static float REMOVE_TIME = 5;
	private DynamicRectangle body;
	private Sprite sprite;

	public WoodDebry(GameEnvironment game, Vector2 position, Vector2 size) {
		super(game);
		float x = position.x - size.x / 2;
		float y = position.y + size.y / 2;
		body = new DynamicRectangle(new Vec4f(x, y, size.x, size.y), DENSITY, RESTITUTION, true);
		addBody(body);

		sprite = new Sprite(game.getResource().getTexture("wood_plank.png"));
		sprite.setSize(size.x, size.y);
	}

	@Override
	public void init() {
	}

	@Override
	public void renderBackground(SpriteBatch batch, float delta) {
		StaticRectangle.updateSprite(sprite, body);
		sprite.draw(batch);
		// chek for remove
		if (body.getBody().getLinearVelocity().isZero()) {
			removeIn(REMOVE_TIME);
		}
	}

	@Override
	public void renderForeground(SpriteBatch batch, float delta) {

	}

}
