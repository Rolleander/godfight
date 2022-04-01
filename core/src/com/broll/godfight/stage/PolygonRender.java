package com.broll.godfight.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.broll.godfight.physic.PhysicBody;
import com.sun.javafx.geom.Vec4f;

public class PolygonRender {

	private PolygonSprite polygonSprite;
	private PolygonRegion region;

	public PolygonRender(Texture texture, PhysicBody body, float textureSize) {
		this(texture, body.getBounds(), body.getPolygonVertices(), textureSize);
	}

	public PolygonRender(Texture texture, Vec4f position, float[] vertices, float textureSize) {
		float x = position.x;
		float y = position.y;
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] *= textureSize;
		}
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		TextureRegion textureRegion = new TextureRegion(texture);

		region = new PolygonRegion(textureRegion, vertices, new EarClippingTriangulator().computeTriangles(vertices)
				.toArray());
		polygonSprite = new PolygonSprite(region);
		polygonSprite.setOrigin(0, 0);
		float rw = (float) texture.getWidth() / textureSize;
		float rh = (float) texture.getHeight() / textureSize;
		polygonSprite.setBounds(x, y, rw, rh);

	}

	public void render(PolygonSpriteBatch batch) {
		polygonSprite.draw(batch);
	}

}
