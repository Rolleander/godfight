package com.broll.godfight.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.broll.godfight.game.GameEnvironment;

public class HudRender {
	private BitmapFont font;
	private GameEnvironment game;
	
	public HudRender(GameEnvironment game) {
		font=new BitmapFont();
		this.game=game;
	}
	
	public void render(float delta, SpriteBatch batch){
		font.setColor(1, 1, 1, 1);
		font.draw(batch, "Objects: "+game.getObjects().count(), 5, Gdx.graphics.getHeight()-20);
	
	}

	
}
