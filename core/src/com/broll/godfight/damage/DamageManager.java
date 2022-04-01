package com.broll.godfight.damage;

import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.bodies.DamageSensor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DamageManager {

	private GameEnvironment game;
	private List<DamageSensor> damgeSensors = new ArrayList<DamageSensor>();

	public DamageManager(GameEnvironment game) {
		this.game = game;
	}

	public void addDamage(DamageSensor damage) {
		damgeSensors.add(damage);
		game.getPhysic().addBody(damage);
	}

	public void update(float delta) {
		Iterator<DamageSensor> iterator = damgeSensors.iterator();
		while (iterator.hasNext()) {
			DamageSensor damage = iterator.next();
			if (damage.update(delta)) {
				// remove
				game.getPhysic().removeBody(damage);
				iterator.remove();
			}
		}
	}
	
	private void doDamage(Fighter fighter, Vector2 location, AttackDamage attackDamage){
		fighter.hit(location, attackDamage);
		game.getParticles().showParticleEffect("blood", location, true);
	}
	
	public boolean collisionCheck(Object target, DamageSensor damage, Vector2 collLocation){
		if(damage.getSourceFighter()!=target){
			if(target instanceof Fighter){
				Fighter fighter=(Fighter)target;
				if(fighter.getTeam().isEnemy(damage.getTeam())){
					if(damage.canHit(fighter)){
						damage.hit(fighter);
						doDamage(fighter, collLocation, damage.getDamage());
						return true;
					}
				}
			}
			else{
				if(damage.canHit(target)){
					damage.hit(target);
					return true;
				}
			}
			
		}
		return false;
	}

}
