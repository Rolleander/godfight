package com.broll.godfight.fighter;

import com.broll.godfight.fighter.moves.Move;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.input.ButtonCombination;

import java.util.ArrayList;
import java.util.List;

public abstract class BattleMove {

	private List<Move> moveset;
	private List<BattleMove> combos;
	private float priority;
	private ButtonCombination button;
	private boolean inAir=true;
	private boolean onGround=true;
	private boolean isCombo=false;
	private boolean active=false;
	private float comboTime=0.5f;
	
	
	public void setButtonCombination(ButtonCombination button) {
		this.button = button;
	}
	
	public void setComboTime(float comboTime) {
		this.comboTime = comboTime;
	}
	
	public void setPriority(float priority) {
		this.priority = priority;
	}
	
	public void buildMove(Fighter fighter)
	{
		moveset=new ArrayList<Move>();
		combos=new ArrayList<BattleMove>();
		build(fighter);
	}
	
	public abstract void build(Fighter fighter);
	
	protected void combo(BattleMove combo){
		combos.add(combo);
	}
	
	protected void add(Move move){
		moveset.add(move);
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public void setCombo(boolean isCombo) {
		this.isCombo = isCombo;
	}
	
	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}
	
	public boolean isInAir() {
		return inAir;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public ButtonCombination getButtonCombination() {
		return button;
	}
	
	public float getPriority() {
		return priority;
	}
	
	public boolean isCombo() {
		return isCombo;
	}
	
	public List<Move> getMoveset() {
		return moveset;
	}
	
	public List<BattleMove> getCombos() {
		return combos;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public float getComboTime() {
		return comboTime;
	}
}
