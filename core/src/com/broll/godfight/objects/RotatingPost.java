package com.broll.godfight.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.physic.bodies.DynamicRectangle;
import com.broll.godfight.physic.bodies.KinematicRectangle;
import com.broll.godfight.physic.bodies.StaticRectangle;
import com.broll.godfight.resource.ResourceManager;
import com.broll.godfight.utils.Vector4;

public class RotatingPost extends StageObject {

	private KinematicRectangle arm;
	private DynamicRectangle platform;
	private Sprite armSprite, platformSprite;
	private float anchorPoint;

	public RotatingPost(GameEnvironment game, Vector2 position, Vector2 armSize, Vector2 platformSize) {
		super(game);
		float x = position.x - armSize.x / 2;
		float y = position.y + armSize.y / 2;
		arm = new KinematicRectangle(new Vector4(x, y, armSize.x, armSize.y));
		platform = new DynamicRectangle(new Vector4(0, 0, platformSize.x, platformSize.y), 150, 0.7f);
		addBody(arm);
		addBody(platform);
		anchorPoint = -armSize.x / 2 + armSize.y / 2;
		ResourceManager rm = game.getResource();
		armSprite = new Sprite(rm.getTexture("pillar.png"));
		armSprite.setSize(armSize.x, armSize.y);
		platformSprite = new Sprite(rm.getTexture("pillar.png"));
		platformSprite.setSize(platformSize.x, platformSize.y);
		arm.noCollision();

	}

	public void setRotationSpeed(float speed) {
		arm.getBody().setAngularVelocity(speed);
	}

	@Override
	public void init() {
		RevoluteJointDef def = new RevoluteJointDef();
		def.bodyA = arm.getBody();
		def.bodyB = platform.getBody();
		def.collideConnected = false;
		def.localAnchorA.set(anchorPoint, 0);
		def.localAnchorB.set(0, 0);
		game.getPhysic().addJoint(def);
		setRotationSpeed(1);
	}

	@Override
	public void renderBackground(SpriteBatch batch, float delta) {
		StaticRectangle.updateSprite(armSprite, arm);
		armSprite.draw(batch);
	}

	@Override
	public void renderForeground(SpriteBatch batch, float delta) {
		StaticRectangle.updateSprite(platformSprite, platform);
		platformSprite.draw(batch);
	}

}
