package com.broll.godfight.input;


public abstract class ButtonCombination {


	public static ButtonCombination buttonsPressed(Button... button) {
		return new SimpleButtonPress(button);
	}

	public static ButtonCombination smashPressed(Button early, Button... button) {
		return new SmashButtonPress(early, button);
	}
	
	public abstract boolean checkCombination(float time,InputLayout layout);

	public abstract void reset();
}
