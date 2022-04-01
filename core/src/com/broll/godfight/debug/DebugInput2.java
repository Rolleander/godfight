package com.broll.godfight.debug;

import com.broll.godfight.input.Button;
import com.broll.godfight.input.InputLayout;

public class DebugInput2 implements InputLayout {

	
	boolean left,right;
	@Override
	public boolean buttonPressed(Button button) {
		
		if(button== Button.LEFT){
			return left;
		}
		if(button== Button.RIGHT){
			return right;
		}
		if(button== Button.UP){
			return Math.random()<0.006;
		}
		
		if(Math.random()<0.05){
			left=Math.random()<0.5;
			right=Math.random()<0.5;
			
		}
		return false;
	}

	@Override
	public boolean buttonClicked(Button button) {
		return false;
	}

}
