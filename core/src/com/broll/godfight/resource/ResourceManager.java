package com.broll.godfight.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {

	private AssetManager manager;
	public final static String RESOURCE_PATH = "data/";
	private final static String TEXTURES_PATH = RESOURCE_PATH + "textures/";
	private final static String PARTICLES_PATH = RESOURCE_PATH + "particles/";
	private final static String PARTICLES_TEXTURES_PATH = PARTICLES_PATH + "textures/";

	public ResourceManager() {
		manager = new AssetManager();
	}

	public void startLoading() {
		FileHandle[] files = Gdx.files.internal(TEXTURES_PATH).list();
		for (FileHandle file : files) {
			manager.load(TEXTURES_PATH + file.name(), Texture.class);
		}
		manager.finishLoading();
	}

	public Texture getTexture(String name) {
		return manager.get(TEXTURES_PATH + name, Texture.class);
	}

	public FileHandle getParticleFile(String name) {
		return Gdx.files.internal(PARTICLES_PATH + name);
	}

	public FileHandle getParticleTexturesFolder() {
		return Gdx.files.internal(PARTICLES_TEXTURES_PATH);
	}
}
