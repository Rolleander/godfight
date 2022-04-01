package com.broll.godfight.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.broll.godfight.input.Button;
import com.broll.godfight.input.InputLayout;

public class DebugInput implements InputLayout{

	@Override
	public boolean buttonPressed(Button button) {
		return Gdx.input.isKeyPressed(getKey(button)); 
	}

	@Override
	public boolean buttonClicked(Button button) {
		return Gdx.input.isKeyJustPressed(getKey(button));
	}


	private int getKey(Button button) {
		switch(button){
		case ATTACK: return Keys.O;
		case BLOCK: return Keys.P;
		case DOWN: return Keys.S;
		case LEFT: return Keys.A;
		case RIGHT: return Keys.D;
		case SPECIAL: return Keys.I;
		case UP: return Keys.W;
		}
		return -1;
	}

}
