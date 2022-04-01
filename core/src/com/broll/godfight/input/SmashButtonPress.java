package com.broll.godfight.input;

public class SmashButtonPress extends ButtonCombination {

	private Button[] buttons;
	private Button earlyButton;

	public SmashButtonPress(Button earlyButton,Button[] button) {
		this.buttons = button;
		this.earlyButton=earlyButton;
	}

	public Button getEarlyButton() {
		return earlyButton;
	}
	
	public Button[] getButtons() {
		return buttons;
	}

	@Override
	public boolean checkCombination(float time, InputLayout layout) {
		return false;
	}

	@Override
	public void reset() {
	}

}
