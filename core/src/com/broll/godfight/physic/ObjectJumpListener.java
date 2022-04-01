package com.broll.godfight.physic;

public interface ObjectJumpListener {

	public void onLanding();
	
	public void onJumping();
	
	public void onFalling();
	
	public void onGettingUp();
	
	public void onFlung();
	
	public void onStandingAgain();
}
