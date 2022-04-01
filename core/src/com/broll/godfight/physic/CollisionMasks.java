package com.broll.godfight.physic;

public class CollisionMasks {

	public static short getMask(CollisionFlag flag) {
		switch (flag) {
		
		case STAGE:
			return (short) (CollisionFlag.PLAYER.getGroup() | CollisionFlag.STAGE.getGroup()
					| CollisionFlag.OBJECT.getGroup() | CollisionFlag.DEBRY.getGroup());
		
		case PLATFORM:
			return (short) (CollisionFlag.PLAYER_FALLING.getGroup());

		case OBJECT:
			return (short) (CollisionFlag.PLAYER.getGroup() | CollisionFlag.STAGE.getGroup() | CollisionFlag.OBJECT
					.getGroup());
		
		case PLAYER:
			return (short) (CollisionFlag.STAGE.getGroup() | CollisionFlag.OBJECT.getGroup());

		case DEBRY:
			return CollisionFlag.STAGE.getGroup();
		
		}
		return -1;
	}

}
