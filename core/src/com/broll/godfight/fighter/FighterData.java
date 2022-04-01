package com.broll.godfight.fighter;

import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.input.ButtonCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class FighterData {

	// Fighter name
	protected String name;
	// Selection Texture name
	protected String selectionImage;
	// Spriteset Texture name
	protected String fighterSpriteset;
	// Fighter width
	protected float width;
	// Fighter height
	protected float height;
	// Sprite height
	protected float spriteHeight;
	// Fighter weight
	protected float weight;
	// Acceleration for Moving
	protected float moveImpulse;
	// Damping of impulse while airborn
	protected float airMovement;
	// Acceleration for Jumping
	protected float jumpImpulse;
	// Max. Movementspeed
	protected float speed;
	// Number of consecutive Jumps
	protected int jumpCount;
	// Resistance to getting punched away
	protected float balance;
	// Moveset
	private List<BattleMove> moves = new ArrayList<BattleMove>();
	// Standard Animation
	private HashMap<FighterPhases,SpriteAnimation> spriteAnimations=new HashMap<FighterPhases,SpriteAnimation>();
	
	protected void setSpritePhase(FighterPhases phase, SpriteAnimation animation) {
		spriteAnimations.put(phase, animation);
	}
	
	protected void setSpritePhase(FighterPhases phase, final int frame){
		setSpritePhase(phase, new SpriteAnimation() {		
			@Override
			public void build(Fighter fighter) {
				frame(frame);
				wait(0.2f);
			}
		});
	}
	
	protected BattleMove createMove(ButtonCombination combination, float priority, BattleMove move){
		move.setButtonCombination(combination);
		move.setPriority(priority);
		addMove(move);
		return move;
	}
	
	protected BattleMove createCombo(ButtonCombination combination, float priority, BattleMove move){
		move=createMove(combination, priority, move);
		move.setCombo(true);
		return move;
	}
	
	protected void addMove(BattleMove move) {
		moves.add(move);
	}

	public String getName() {
		return name;
	}

	public String getSelectionImage() {
		return selectionImage;
	}

	public String getFighterSpriteset() {
		return fighterSpriteset;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}

	public float getMoveImpulse() {
		return moveImpulse;
	}

	public float getJumpImpulse() {
		return jumpImpulse;
	}

	public float getSpeed() {
		return speed;
	}

	public int getJumpCount() {
		return jumpCount;
	}

	public float getBalance() {
		return balance;
	}

	public List<BattleMove> getMoves() {
		return moves;
	}
	
	public float getSpriteHeight() {
		return spriteHeight;
	}

	public float getAirMovement() {
		return airMovement;
	}
	
	public HashMap<FighterPhases, SpriteAnimation> getSpriteAnimations() {
		return spriteAnimations;
	}
}
