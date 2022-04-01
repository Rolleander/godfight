package com.broll.godfight.stage;

import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.GodFightGame;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.sun.javafx.geom.Vec4f;

import java.awt.geom.Point2D;

public class CameraController {

	private GameEnvironment game;
	// prevents from zooming to far in
	private static final float ZOOM_BORDER = 3; // m
	private static final float ZOOM_SPEED = 0.025f;
	private static final float MOVE_SPEED = 0.25f;
	private Vec4f lastBox = new Vec4f(0, 0, 0, 0);

	public CameraController(GameEnvironment game) {
		this.game = game;
	}

	public void update(float delta) {
		// Vec4f bounds = game.getStage().getBounds();
		Vec4f box = getPlayerBox();
		Vector2 center = getBoxCenter(box);
		addMapBorder(box);
		updateCamera(center, getZoom(box));
	}

	private void updateCamera(Vector2 targetLocation, float targetZoom) {
		GodFightGame container = game.getContainer();
		float zoom = container.gameCamera.zoom;
		float x = container.gameCamera.position.x;
		float y = container.gameCamera.position.y;
		float targetX = targetLocation.x;
		float targetY = targetLocation.y;
		container.gameCamera.position.x = getNewCameraValue(x, targetX, MOVE_SPEED);
		container.gameCamera.position.y = getNewCameraValue(y, targetY, MOVE_SPEED);
		container.gameCamera.zoom = getNewCameraValue(zoom, targetZoom, ZOOM_SPEED);
	}

	private float getNewCameraValue(float old, float target, float max) {
		float distance = (float) Point2D.distance(old, 0, target, 0);
		distance = Math.min(distance, max);
		if (target > old) {
			return old + distance;
		}
		return old - distance;
	}

	private float getZoom(Vec4f box) {
		float width = (float) Point2D.distance(box.x, 0, box.z, 0);
		float height = (float) Point2D.distance(box.y, 0, box.w, 0);
		float zoom1 = height / GodFightGame.CAMERA_HEIGHT;
		float zoom2 = width / GodFightGame.CAMERA_WIDTH;
		return Math.max(zoom1, zoom2);
	}

	private Vector2 getBoxCenter(Vec4f box) {
		Vector2 center = new Vector2();
		center.x = (float) (box.x + Point2D.distance(box.x, 0, box.z, 0) / 2);
		center.y = (float) (box.y - Point2D.distance(box.y, 0, box.w, 0) / 2);
		return center;
	}

	private void addMapBorder(Vec4f box) {
		float w = ZOOM_BORDER * (GodFightGame.CAMERA_WIDTH / GodFightGame.CAMERA_HEIGHT);
		float h = ZOOM_BORDER * (GodFightGame.CAMERA_HEIGHT / GodFightGame.CAMERA_WIDTH);
		box.x -= w;
		box.z += w;
		box.y += h;
		box.w -= h;
	}

	private Vec4f getPlayerBox() {
		Vec4f vec = null;
		for (Fighter fighter : game.getFighters()) {
			if (fighter.getAttributes().isOnStage()) {
				Vector2 position = fighter.getPlayerBody().getBody().getPosition();
				if (vec == null) {
					vec = new Vec4f(position.x, position.y, position.x, position.y);
				} else {
					if (position.x < vec.x) {
						vec.x = position.x;
					} else if (position.x > vec.z) {
						vec.z = position.x;
					}
					if (position.y < vec.w) {
						vec.w = position.y;
					} else if (position.y > vec.y) {
						vec.y = position.y;
					}
				}
			}
		}
		if (vec != null) {
			lastBox = new Vec4f(vec.x, vec.y, vec.z, vec.w);
			;
		} else {
			// if no players alive show the last zoombox
			return new Vec4f(lastBox.x, lastBox.y, lastBox.z, lastBox.w);
		}
		return vec;
	}
}
