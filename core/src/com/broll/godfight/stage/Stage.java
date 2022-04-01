package com.broll.godfight.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.physic.GamePhysic;
import com.broll.godfight.physic.PhysicBody;
import com.sun.javafx.geom.Vec4f;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage {
	protected GameEnvironment game;
	protected Vec4f bounds;
	protected CameraController cameraController;
	private List<PhysicBody> bodies = new ArrayList<PhysicBody>();
	private Array<PolygonRender> renders = new Array<PolygonRender>();
	private PolygonSpriteBatch polygonBatch;

	public Stage(GameEnvironment game) {
		this.game = game;
		cameraController = new CameraController(game);
		polygonBatch = new PolygonSpriteBatch();
	}

	public abstract void buildStage();

	public abstract StageLocation getStartLocation(Fighter fighter);
	


	protected void renderStageRenders() {
		game.getContainer().batch.end();
		polygonBatch.setProjectionMatrix(game.getContainer().gameCamera.combined);
		polygonBatch.begin();
		for (PolygonRender render : renders) {
			render.render(polygonBatch);
		}
		polygonBatch.end();
		game.getContainer().batch.begin();
	}
	

	public void renderStageBackground() {
		renderBackground();
	}

	public void renderStageForeground() {
		renderForeground();
	}

	public abstract void renderForeground();

	public abstract void renderBackground();

	public void updateStage(float delta) {
		cameraController.update(delta);
		update(delta);
	}

	protected abstract void update(float delta);

	protected void addRender(PhysicBody body, Texture texture, float textureSize) {
		addRender(new PolygonRender(texture, body, textureSize));
	}

	protected void addRender(PolygonRender render) {
		renders.add(render);
	}

	protected PhysicBody addBody(PhysicBody body) {
		bodies.add(body);
		Vec4f bound = body.getBounds();
		if (bounds == null) {
			bounds = new Vec4f(bound);
		} else {
			bounds.x = Math.min(bounds.x, bound.x);
			bounds.y = Math.max(bounds.y, bound.y);
			bounds.z = Math.max(bounds.z, bound.z);
			bounds.w = Math.min(bounds.w, bound.w);
		}
		return body;
	}

	public void addBodies(GamePhysic physic) {
		for (PhysicBody body : bodies) {
			physic.addBody(body);
		}
	}

	public Vec4f getBounds() {
		return bounds;
	}
}
