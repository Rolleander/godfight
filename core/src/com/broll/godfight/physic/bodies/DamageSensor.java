package com.broll.godfight.physic.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.broll.godfight.damage.AttackDamage;
import com.broll.godfight.fighter.FighterTeam;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.PhysicBody;

import java.util.ArrayList;
import java.util.List;

public abstract class DamageSensor extends PhysicBody {

	protected AttackDamage damage;
	private float duration;
	private float runtime;
	private List<Object> hitList = new ArrayList<Object>();
	private Fighter sourceFighter;
	private FighterTeam sourceTeam = FighterTeam.NONE;
	private boolean multipleHits = false;
	protected float offsetX, offsetY;

	public DamageSensor(AttackDamage damage) {
		this.damage = damage;
	}

	@Override
	public BodyDef createDefinition() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		Vector2 playerLocation = sourceFighter.getPlayerBody().getBody().getPosition();
		float x = playerLocation.x;
		float y = playerLocation.y;
		if (sourceFighter.getSprite().isFacingLeft()) {
			x -= offsetX;
			damage.swapImpulseVector();
		} else {
			x += offsetX;
		}
		y += offsetY;
		bodyDef.position.set(x, y);
		return bodyDef;
	}

	public void init(Fighter fighter) {
		this.sourceFighter = fighter;
		if (fighter != null) {
			sourceTeam = fighter.getTeam();
		}
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public void setMultipleHits(boolean multipleHits) {
		this.multipleHits = multipleHits;
	}

	protected abstract void updateDamage(float delta);

	public boolean update(float delta) {
		runtime += delta;
		updateDamage(delta);
		return runtime >= duration;
	}

	public boolean canHit(Object fighter) {
		if (multipleHits) {
			return true;
		} else {
			return !hitList.contains(fighter);
		}
	}

	public void hit(Object fighter) {
		if (!multipleHits) {
			hitList.add(fighter);
		}
	}

	public Fighter getSourceFighter() {
		return sourceFighter;
	}

	public AttackDamage getDamage() {
		return damage;
	}

	public FighterTeam getTeam() {
		return sourceTeam;
	}
}
