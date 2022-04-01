package com.broll.godfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.broll.godfight.debug.DebugFactory;
import com.broll.godfight.resource.ResourceManager;
import com.broll.godfight.screens.GameScreen;

public class GodFightGame extends Game {
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	public final static float CAMERA_WIDTH=16;
	public final static float CAMERA_HEIGHT=9;
	
	
	public SpriteBatch batch;
	public BitmapFont font;
	public ResourceManager resource;
	public OrthographicCamera hudCamera;
	public OrthographicCamera gameCamera;	
	public GameScreen gameScreen;
	
	@Override
	public void create () {
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, WIDTH ,HEIGHT);
		gameCamera = new OrthographicCamera();
		gameCamera.setToOrtho(false, CAMERA_WIDTH , CAMERA_HEIGHT );
	
		batch = new SpriteBatch();
		font=new BitmapFont();
		resource=new ResourceManager();
		resource.startLoading();
		
		gameScreen=new GameScreen(this);
		//debug
		gameScreen.init(DebugFactory.createDebugEnvironment(this));
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.setProjectionMatrix(hudCamera.combined);
		batch.begin();
		font.setColor(1, 1, 1, 1);
		font.draw(batch, "FPS "+fps, 5, Gdx.graphics.getHeight()-5);
		batch.end();
	}
	
	@Override
	public void dispose () {
		gameScreen.dispose();
		batch.dispose();
		font.dispose();
	}
}
