package com.broll.godfight.physic;

public enum CollisionFlag {

	STAGE(0x0001), PLATFORM(0x0002), PLAYER(0x0004), PLAYER_FALLING(0x0008),DAMAGE_SENSOR(0x0010),OBJECT(0x0020),DEBRY(0x0040);

	private short group;

	CollisionFlag(int group) {
		this.group = (short) group;

	}

	public short getGroup() {
		return group;
	}
	

}
