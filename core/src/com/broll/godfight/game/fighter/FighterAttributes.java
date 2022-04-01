package com.broll.godfight.game.fighter;

public class FighterAttributes {

	public final static int INFINITE_STOCKS=-1;
	private final static float RESPAWN_TIME=5;
	private float percent;
	private int stocks;
	private boolean onStage;
	private float respawnTime;
	

	public FighterAttributes(int stocks) {
		this.stocks = stocks;
	}
	
	public void respawn(){
		percent=0;
	}
	
	public void initRespawnTime(){
		respawnTime=RESPAWN_TIME;
	}
	
	public void updateRespawnTime(float delta){
		respawnTime-=delta;
	}
	
	public boolean respawnTimeDone(){
		return respawnTime<=0;
	}
	
	public void setOnStage(boolean onStage) {
		this.onStage = onStage;
	}

	public void doDamage(float percent) {
		this.percent += percent;
	}

	public void doHeal(float percent) {
		this.percent -= percent;
		if (this.percent < 0) {
			this.percent = 0;
		}
	}

	public void useStock() {
		stocks--;
	}

	public boolean stocksLeft() {
		return stocks !=0;
	}

	public float getPercent() {
		return percent;
	}

	public int getStocks() {
		return stocks;
	}
	
	public boolean isOnStage() {
		return onStage;
	}
}
