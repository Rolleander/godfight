package com.broll.godfight.fighter.moves;

import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.bodies.DamageSensor;

public class Damage extends Move {

	private DamageSensor sensor;

	public Damage(DamageSensor sensor) {
		this.sensor = sensor;
	}

	@Override
	protected void enter(Fighter fighter, boolean facingLeft) {
		sensor.init(fighter);
		game.getDamage().addDamage(sensor);
	}

	@Override
	public boolean update(float delta) {
		return true;
	}

}
