package com.broll.godfight.fighter;

import com.broll.godfight.fighter.moves.Move;
import com.broll.godfight.fighter.moves.SetFrame;
import com.broll.godfight.fighter.moves.Wait;
import com.broll.godfight.game.fighter.Fighter;

import java.util.ArrayList;
import java.util.List;

public abstract class SpriteAnimation {

	private List<Move> moveset;
	private boolean loop=false;
	private boolean skippable=false;

	public abstract void build(Fighter fighter);

	public void buildAnimation(Fighter fighter) {
		moveset = new ArrayList<Move>();
		build(fighter);
	}
	
	public void skippable() {
		this.skippable = true;
	}
	
	public void loop(){
		loop=true;
	}

	public void frame(int nr) {
		moveset.add(new SetFrame(nr));
	}

	public void wait(float seconds) {
		moveset.add(new Wait(seconds));
	}

	public List<Move> getMoveset() {
		return moveset;
	}
	
	public boolean isLoop() {
		return loop;
	}
	
	public boolean isSkippable() {
		return skippable;
	}
}
