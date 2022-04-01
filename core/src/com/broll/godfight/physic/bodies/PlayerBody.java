package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.CollisionFlag;
import com.broll.godfight.physic.CollisionMasks;
import com.broll.godfight.physic.DynamicPhysicBody;
import com.broll.godfight.physic.ObjectJumpListener;
import com.broll.godfight.utils.Vector4;

public class PlayerBody extends DynamicPhysicBody {

	private final static int JUMP_STATE_RISE = 1;
	private final static int JUMP_STATE_FALL = 2;
	private final static float STANDUP_IMPULSE = 20;
	private final static float MAX_STANDUP_SPEED = 2f;

	// in seconds
	private final static float JUMP_DELAY = 0.3f;
	private Vector4 pos;
	private int footContacts;
	private float jumpDelay;
	private ObjectJumpListener jumpListener;
	private int jumpState = 0;
	private boolean flung = false;
	private boolean standUp = false;
	private Fixture head, bottom, footPlate;

	private Fighter fighter;

	public PlayerBody(Fighter fighter, Vector4 pos) {
		this.pos = pos;
		this.fighter = fighter;
	}

	public void setJumpListener(ObjectJumpListener jumpListener) {
		this.jumpListener = jumpListener;
	}

	public void teleport(Vector2 location) {
		body.setTransform(location.x, location.y, 0);
		body.setLinearVelocity(0, 0);
	}

	public void reset() {
		flung = false;
		setFixRotation(true);
		setRestiution(0);
		body.setTransform(body.getPosition(), 0);
		standUp = false;
		jumpListener.onStandingAgain();
	}

	public void setFixRotation(boolean fix) {
		body.setFixedRotation(fix);
	}

	public void updateParameters(float weight, float width, float height) {
		float size = width * height;
		float density = weight / size;
		float friction = 0.1f;
		float restitution = 0;
		setPhysicParameters(density, friction, restitution);
	}

	private void setRestiution(float restitution) {
		Array<Fixture> fixtures = body.getFixtureList();
		for (Fixture f : fixtures) {
			f.setRestitution(restitution);
		}
	}

	private void updateCollisionFlags() {
		float yMove = body.getLinearVelocity().y;
		Filter filter = new Filter();
		filter.maskBits = CollisionFlag.PLATFORM.getGroup();
		if ((yMove <=0||jumpState==0) && !isFlung()) {
			// can land on platforms
			filter.categoryBits = CollisionFlag.PLAYER_FALLING.getGroup();
		} else {
			// can go trough platforms
			filter.categoryBits = CollisionFlag.PLAYER.getGroup();
		}
		footPlate.setFilterData(filter);
	}

	private void setFilter(Filter filter) {
		Array<Fixture> fixtures = body.getFixtureList();
		for (Fixture f : fixtures) {
			f.setFilterData(filter);
		}
	}

	private void setPhysicParameters(float density, float friction, float restitution) {
		Array<Fixture> fixtures = body.getFixtureList();
		for (Fixture f : fixtures) {
			f.setDensity(density);
			f.setFriction(friction);
			f.setRestitution(restitution);
		}
		body.resetMassData();
	}

	public void update(float delta) {
		if (jumpDelay > 0) {
			jumpDelay -= delta;
		}
		float ySpeed = body.getLinearVelocity().y;
		float xSpeed = body.getLinearVelocity().x;
		updateCollisionFlags();
		if (flung) {

			if (standUp) {

				float r = body.getAngle();
				float angle = (float) Math.toDegrees(r) % 360;
				if (angle < 0) {
					angle = 360 + angle;
				}

				Transform transform = body.getTransform();
				CircleShape shape = (CircleShape) head.getShape();
				Vector2 vec = new Vector2();
				vec.set(shape.getPosition());
				transform.mul(vec);

				transform = body.getTransform();
				shape = (CircleShape) bottom.getShape();
				Vector2 vec2 = new Vector2();
				vec2.set(shape.getPosition());
				transform.mul(vec2);

				applyImpulse(0, STANDUP_IMPULSE, vec.x, vec.y);
				applyImpulse(0, -STANDUP_IMPULSE, vec2.x, vec2.y);

				if (Math.abs(angle) <= 15) {
					reset();
				}

			} else {
				if (Math.abs(xSpeed) <= MAX_STANDUP_SPEED && Math.abs(ySpeed) <= MAX_STANDUP_SPEED
						&& body.getAngularVelocity() < MAX_STANDUP_SPEED) {
					if (jumpDelay <= 0) {
						standUp = true;
						jumpListener.onGettingUp();
					}
				}
			}
		} else {

			if (jumpState == JUMP_STATE_RISE) {
				if (ySpeed <= 0) {
					jumpListener.onFalling();
					jumpState = JUMP_STATE_FALL;
				}
			} else if (jumpState == JUMP_STATE_FALL) {
				if (onGround()) {
					jumpState = 0;
					jumpListener.onLanding();
				}
			}
		}

	}

	public void incFootContacts() {
		footContacts++;
	}

	public void decFootContacts() {
		footContacts--;
	}

	public boolean canJump() {
		return  jumpDelay <= 0;
	}

	public boolean jumpDelayDone() {
		return jumpDelay <= 0;
	}

	public void jump(float jumpImpulse) {
		body.setLinearVelocity(body.getLinearVelocity().x, 0);
		jumpDelay = JUMP_DELAY;
		applyImpulse(0, jumpImpulse);
		jumpListener.onJumping();
		jumpState = JUMP_STATE_RISE;
	}

	public void throwBody(Vector2 location, Vector2 dir, float impulse) {
		flung = true;
		jumpDelay = JUMP_DELAY;
		setFixRotation(false);
		float xPower = dir.x * impulse;
		float yPower = dir.y * impulse;
		applyImpulse(xPower, yPower, location.x, location.y);
		jumpState = 0;
		setRestiution(0.7f);
		jumpListener.onFlung();
	}

	public boolean isFlung() {
		return flung;
	}

	public boolean isInAir() {
		return footContacts == 0;
	}

	public boolean onGround() {
		return footContacts > 0;
	}

	@Override
	public BodyDef createDefinition() {

		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't
		// move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;

		// Set our body's starting position in the world
		bodyDef.position.set(pos.x, pos.y);
		return bodyDef;
	}

	@Override
	public void init(Body body) {
		body.setLinearDamping(1f);
		// Create a circle shape and set its radius to 6
		float density = 1f;
		float width = pos.z;
		float height = pos.w;

		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width / 2.1f, (height - width) / 2);
		body.createFixture(boxShape, density);
		boxShape.dispose();

		// bottom circle
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(width / 2);
		circleShape.setPosition(new Vector2(0, -(height - width) / 2));
		bottom = body.createFixture(circleShape, density);

		// foot plate for cloud platform detection
		boxShape = new PolygonShape();
		boxShape.setAsBox(width / 2.1f, height * 0.05f, new Vector2(0, -height * 0.4f), 0);
		footPlate = body.createFixture(boxShape, density);
		footPlate.setUserData(this);
		boxShape.dispose();

		// top circle
		circleShape.setPosition(new Vector2(0, (height - width) / 2));
		head = body.createFixture(circleShape, density);
		circleShape.dispose();

		// foot sensor
		float sensorWidth = width * 0.85f;
		float sensorHeight = height * 0.1f;
		PolygonShape sensorShape = new PolygonShape();
		sensorShape.setAsBox(sensorWidth / 2, sensorHeight / 2, new Vector2(0, -height / 2), 0);
		Fixture fixture = body.createFixture(sensorShape, density);
		fixture.setSensor(true);
		fixture.setUserData(this);
		setFixRotation(true);
		body.setUserData(this);
		Filter filter = new Filter();
		filter.maskBits = CollisionMasks.getMask(CollisionFlag.PLAYER);
		filter.categoryBits = CollisionFlag.PLAYER.getGroup();
		setFilter(filter);
		updateCollisionFlags();
	}

	@Override
	public Vector4 getBounds() {
		return null;
	}

	public Fighter getFighter() {
		return fighter;
	}
}
