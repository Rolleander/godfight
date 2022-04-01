package com.broll.godfight.particles;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;

import java.util.HashMap;

public class ParticlesManager {

	private HashMap<String, ParticleEffectPool> particlePools = new HashMap<String, ParticleEffectPool>();
	private GameEnvironment game;
	private Array<PooledEffect> additiveEffects = new Array<PooledEffect>();
	private Array<PooledEffect> normalEffects = new Array<PooledEffect>();
	private final static int POOL_SIZE = 10;

	public ParticlesManager(GameEnvironment game) {
		this.game = game;
	}

	public void showParticleEffect(String name, Vector2 position, boolean additive) {
		ParticleEffectPool pool = particlePools.get(name);
		if (pool == null) {
			ParticleEffect effect = new ParticleEffect();
			effect.load(game.getResource().getParticleFile(name), game.getResource().getParticleTexturesFolder());
			effect.scaleEffect(0.01f);
			for (ParticleEmitter emitter : effect.getEmitters()) {
				emitter.getGravity().setHigh(-2.5f);
				emitter.getGravity().setActive(true);
			}

			effect.setEmittersCleanUpBlendFunction(!additive);
			pool = new ParticleEffectPool(effect, 1, POOL_SIZE);
			particlePools.put(name, pool);
		}

		PooledEffect effect = pool.obtain();
		
		effect.setPosition(position.x, position.y);
		if (additive) {
			additiveEffects.add(effect);
		} else {
			normalEffects.add(effect);
		}
	}

	public void render(float delta) {
		SpriteBatch batch = game.getContainer().batch;
		// draw all additive blended effects
		for (int i = additiveEffects.size - 1; i >= 0; i--) {
			PooledEffect effect = additiveEffects.get(i);
			effect.draw(batch, delta);
			if (effect.isComplete()) {
				effect.free();
				additiveEffects.removeIndex(i);
			}
		}

		// We need to reset the batch to the original blend state as we have
		// setEmittersCleanUpBlendFunction as false in additiveEffect
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		// draw all 'normal alpha' blended effects
		for (int i = normalEffects.size - 1; i >= 0; i--) {
			PooledEffect effect = normalEffects.get(i);
			effect.draw(batch, delta);
			if (effect.isComplete()) {
				effect.free();
				normalEffects.removeIndex(i);
			}
		}

	}

	public void clearEffects() {
		for (int i = additiveEffects.size - 1; i >= 0; i--) {
			additiveEffects.get(i).free(); // free all the effects back to the
											// pool
			additiveEffects.clear(); // clear the current effects array
		}
		for (int i = normalEffects.size - 1; i >= 0; i--) {
			normalEffects.get(i).free(); // free all the effects back to the
											// pool
			normalEffects.clear(); // clear the current effects array
		}
	}
}
