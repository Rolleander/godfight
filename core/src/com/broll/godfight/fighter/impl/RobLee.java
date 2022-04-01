package com.broll.godfight.fighter.impl;

import com.broll.godfight.damage.AttackDamage;
import com.broll.godfight.damage.CircleDamageSensor;
import com.broll.godfight.fighter.BattleMove;
import com.broll.godfight.fighter.FighterData;
import com.broll.godfight.fighter.FighterPhases;
import com.broll.godfight.fighter.SpriteAnimation;
import com.broll.godfight.fighter.moves.Damage;
import com.broll.godfight.fighter.moves.Impulse;
import com.broll.godfight.fighter.moves.Jump;
import com.broll.godfight.fighter.moves.SetFrame;
import com.broll.godfight.fighter.moves.Wait;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.input.Button;
import com.broll.godfight.input.ButtonCombination;

public class RobLee extends FighterData {

	public RobLee() {
		// NAME
		name = "Rob Lee";
		// TEXTURES
		selectionImage = "rob_selection.png";
		fighterSpriteset = "rob_spriteset.png";
		// MOVEMENT
		jumpCount = 2;
		jumpImpulse = 1000;
		moveImpulse = 30;
		airMovement = 0.6f;
		speed = 5;
		// PHYSIC
		width = 0.7f; // meter
		height = 1.8f;// meter
		spriteHeight = 2.5f; // meter
		weight = 80; // kg
		balance = 400;
		// Sprite Animation
		animation();
		// MOVESET
		attackUp();
		normalAttack();
		jump();
	}
	
	private void animation()
	{
		setSpritePhase(FighterPhases.IDLE, 1);
		setSpritePhase(FighterPhases.JUMP, 10);
		setSpritePhase(FighterPhases.WALK, new SpriteAnimation() {
			@Override
			public void build(Fighter fighter) {
				float wait=0.2f;
				frame(4);
				wait(wait);
				frame(5);
				wait(wait);
				frame(6);
				wait(wait);			
				loop();
				skippable();
			}
		});
		setSpritePhase(FighterPhases.FALL, 2);
		setSpritePhase(FighterPhases.HIT, 20);
		setSpritePhase(FighterPhases.FLUNG, 21);
		setSpritePhase(FighterPhases.ARISE, 23);
		
		
	}

	private void jump(){
		createMove(ButtonCombination.buttonsPressed(Button.UP), 0, new BattleMove() {
			@Override
			public void build(Fighter fighter) {
				add(new Jump());
				add(new Wait(0.1f));	
			}
		});
		
	}
	
	private void normalAttack() {
		//punch combo1
		final BattleMove combo1=createCombo(ButtonCombination.buttonsPressed(Button.ATTACK), 10, new BattleMove() {
			@Override
			public void build(Fighter fighter) {
				add(new Impulse(150, 0));
				add(new Damage(new CircleDamageSensor(1.2f, 0.4f, 0.45f, 0.2f, new AttackDamage(7, 50, 90))));
				
				add(new SetFrame(12));
				add(new Wait(0.15f));		
			}
		});
	
		//punch 1
		createMove(ButtonCombination.buttonsPressed(Button.ATTACK), 10, new BattleMove() {
			@Override
			public void build(Fighter fighter) {
				add(new Impulse(150, 0));
				add(new Damage(new CircleDamageSensor(1.2f, 0.4f, 0.45f, 0.2f, new AttackDamage(7, 50,90))));
				add(new SetFrame(11));
				add(new Wait(0.15f));
				combo(combo1);
			}
		});
	}

	private void attackUp() {
		createMove(ButtonCombination.buttonsPressed(Button.ATTACK, Button.UP), 10, new BattleMove() {
			@Override
			public void build(Fighter fighter) {
			//	add(new StopMovement(true));
				add(new Impulse(200, 300));
				add(new SetFrame(13));
				add(new Wait(0.1f));
				add(new Damage(new CircleDamageSensor(0.6f, 0.7f, 0.75f, 0.2f, new AttackDamage(12, 700,0))));
				add(new Wait(0.1f));
				//add(new Impulse(0, 2000));
				add(new SetFrame(14));
				add(new Wait(0.2f));
				
			}
		});
	}
}
