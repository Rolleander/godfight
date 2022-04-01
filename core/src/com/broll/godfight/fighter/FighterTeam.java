package com.broll.godfight.fighter;

public enum FighterTeam {

	NONE, BLUE, RED, GREEN, YELLOW;

	public boolean isEnemy(FighterTeam otherTeam) {
		if (this == NONE) {
			return true;
		} else if (otherTeam == NONE) {
			return true;
		} else {
			return this != otherTeam;
		}
	}

}
