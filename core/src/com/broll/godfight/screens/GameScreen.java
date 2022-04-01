package com.broll.godfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.broll.godfight.GodFightGame;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.hud.HudRender;

public class GameScreen implements Screen {

	private GodFightGame container;
	private GameEnvironment game;
	private HudRender hudRender;
	
	public GameScreen(GodFightGame container) {
		this.container=container;
	}
	
	public void init(GameEnvironment environment){
		this.game=environment;
		this.hudRender=new HudRender(game);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getStageObserver().update(delta);
		game.getStage().updateStage(delta);
		game.getDamage().update(delta);
		for(Fighter fighter: game.getFighters()){
			fighter.update(delta);
		}
		container.gameCamera.update();		
		//GAME RENDER
		container.batch.setProjectionMatrix(container.gameCamera.combined);
		container.batch.begin();
		game.getStage().renderStageBackground();
		game.getObjects().renderBackground(delta);
		game.getObjects().renderForeground(delta);
		for(Fighter fighter: game.getFighters()){
			fighter.render(container.batch);
		}
		game.getParticles().render(delta);
		game.getStage().renderStageForeground();
		container.batch.end();
		//PHYSIC DEBUG RENDER
		//game.getPhysic().debugRender(container.gameCamera);
		container.batch.setProjectionMatrix(container.hudCamera.combined);
		container.batch.begin();
		//HUD RENDER
		hudRender.render(delta,container.batch);
		container.batch.end();
		
		// update physic
		game.getPhysic().doPhysicStep(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
