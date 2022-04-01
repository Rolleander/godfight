package com.broll.godfight.input;

public class SimpleButtonPress extends ButtonCombination {

	private Button[] buttons;
	private boolean[] pressed, notPressed;

	private boolean stepTwo = false;
	private boolean success = false;

	public SimpleButtonPress(Button[] button) {
		this.buttons = button;
		pressed = new boolean[button.length];
		notPressed = new boolean[button.length];
	}

	public Button[] getButtons() {
		return buttons;
	}

	@Override
	public void reset() {
		for (int i = 0; i < buttons.length; i++) {
			pressed[i] = false;
			notPressed[i]=false;
		}
		stepTwo = false;
		success = false;
	}

	@Override
	public boolean checkCombination(float time, InputLayout layout) {
		if (success == true) {
			return true;
		} else {
			if (stepTwo) {
				boolean allTrue = true;
				for (int i = 0; i < buttons.length; i++) {
					if (layout.buttonPressed(buttons[i])) {
						pressed[i] = true;
					}
					if (pressed[i] == false) {
						allTrue = false;
					}
				}

				if (allTrue) {
					success = true;
				}

			} else {
				boolean allTrue = true;
				for (int i = 0; i < buttons.length; i++) {
					if (!layout.buttonPressed(buttons[i])) {
						notPressed[i] = true;
					}
					if (notPressed[i] == false) {
						allTrue = false;
					}
				}
				if (allTrue) {
					stepTwo = true;
				}
			}
		}
		return false;
	}

}
