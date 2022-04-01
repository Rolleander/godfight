package com.broll.godfight.debug;

import com.broll.godfight.GodFightGame;
import com.broll.godfight.fighter.FighterData;
import com.broll.godfight.fighter.FighterTeam;
import com.broll.godfight.fighter.impl.RobLee;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.input.InputLayout;
import com.broll.godfight.stage.TestStage;

public class DebugFactory {

	public static GameEnvironment createDebugEnvironment(GodFightGame container) {
		GameEnvironment environment = new GameEnvironment(container,container.resource);
		int stock=5;
		environment.init(new TestStage(environment));

		FighterData data = new RobLee();
		InputLayout input = new DebugInput();
		Fighter testPlayer = new Fighter(environment, data, input,FighterTeam.NONE,stock);
		environment.addFighter(testPlayer);

		for(int i=0; i<1; i++)
		{
		data = new RobLee();
		input = new DebugInput2();
		
		testPlayer = new Fighter(environment, data, input,FighterTeam.NONE,stock);
		environment.addFighter(testPlayer);
		}
		
		return environment;
	}

}
