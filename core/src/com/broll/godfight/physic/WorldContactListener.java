package com.broll.godfight.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.bodies.DamageSensor;
import com.broll.godfight.physic.bodies.PlayerBody;

public class WorldContactListener implements ContactListener {

	private GameEnvironment game;
	private Array<ObjectHit> hits = new Array<ObjectHit>();

	public WorldContactListener(GameEnvironment game) {
		this.game = game;
	}

	public void processObjectHits() {
		if (hits.size > 0) {
			for (ObjectHit hit : hits) {
				hit.getBody().onHit(hit.getSensor());
			}
			hits.clear();
		}
	}

	private boolean isObject(Fixture fixture) {
		return fixture.getFilterData().categoryBits == CollisionFlag.OBJECT.getGroup();
	}

	private boolean isPlayer(Fixture fixture) {
		short bits = fixture.getFilterData().categoryBits;
		return bits == CollisionFlag.PLAYER.getGroup() || bits == CollisionFlag.PLAYER_FALLING.getGroup();
	}

	private boolean isStage(Fixture fixture) {
		short bits = fixture.getFilterData().categoryBits;
		return bits == CollisionFlag.STAGE.getGroup() || bits == CollisionFlag.PLATFORM.getGroup()
				|| bits == CollisionFlag.OBJECT.getGroup();
	}

	private boolean isDamage(Fixture fixture) {
		return fixture.getFilterData().categoryBits == CollisionFlag.DAMAGE_SENSOR.getGroup();
	}

	/*
	 * Check Player with Damage Sensor collision
	 */
	private void checkDamageCollision(PlayerBody playerBody, DamageSensor damage, Contact contact) {
		// damage hit player
		Fighter fighter = playerBody.getFighter();
		Vector2 playerPosition = playerBody.getBody().getPosition();
		Vector2 sensorPosition = damage.getBody().getPosition();
		float x = (playerPosition.x + sensorPosition.x) / 2;
		float y = (playerPosition.y + sensorPosition.y) / 2;
		Vector2 location = new Vector2(x, y);
		game.getDamage().collisionCheck(fighter, damage, location);
	}

	/*
	 * Check Object with Damage Sensor collision
	 */
	private void checkObjectCollision(DamageSensor damage, PhysicBody object, Contact contact) {
		// punch object away
		Vector2 objectPosition = object.getBody().getPosition();
		Vector2 sensorPosition = damage.getBody().getPosition();
		float x = (objectPosition.x + sensorPosition.x) / 2;
		float y = (objectPosition.y + sensorPosition.y) / 2;
		Vector2 location = new Vector2(x, y);
		float power = damage.getDamage().getPower();
		Vector2 impulse = damage.getDamage().getImpulseVector().cpy();
		impulse.x = impulse.x * power;
		impulse.y = impulse.y * power;
		if (game.getDamage().collisionCheck(object, damage, location)) {
			object.getBody().applyLinearImpulse(impulse, location, true);
			hits.add(new ObjectHit(object, damage));
		}
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixturea = contact.getFixtureA();
		Fixture fixtureb = contact.getFixtureB();
		Object adata = fixturea.getUserData();
		Object bdata = fixtureb.getUserData();
		// System.out.println(adata+"  "+bdata);

		if (bdata instanceof DamageSensor) {
			if (isPlayer(fixturea)) {
				checkDamageCollision((PlayerBody) fixturea.getBody().getUserData(), (DamageSensor) bdata, contact);
			} else if (isObject(fixturea)) {
				checkObjectCollision((DamageSensor) bdata, (PhysicBody) fixturea.getBody().getUserData(), contact);
			}
		} else if (adata instanceof DamageSensor) {
			if (isPlayer(fixtureb)) {
				checkDamageCollision((PlayerBody) fixtureb.getBody().getUserData(), (DamageSensor) adata, contact);
			} else if (isObject(fixturea)) {
				checkObjectCollision((DamageSensor) adata, (PhysicBody) fixtureb.getBody().getUserData(), contact);
			}
		}

		if (adata instanceof PlayerBody) {
			if (isStage(fixtureb)) {
				((PlayerBody) adata).incFootContacts();
			}

		}
		if (bdata instanceof PlayerBody) {
			if (isStage(fixturea)) {
				{
					((PlayerBody) bdata).incFootContacts();
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixturea = contact.getFixtureA();
		Fixture fixtureb = contact.getFixtureB();
		Object adata = fixturea.getUserData();
		Object bdata = fixtureb.getUserData();

		if (adata instanceof PlayerBody) {
			if (isStage(fixtureb)) {
				((PlayerBody) adata).decFootContacts();
			}
		}

		if (bdata instanceof PlayerBody) {
			if (isStage(fixturea)) {
				{
					((PlayerBody) bdata).decFootContacts();
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
