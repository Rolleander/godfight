package com.broll.godfight.resource;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FighterSprite {

	private Texture texture;
	private final static int SPRITE_SIZE=300;
	private final static int SPRITESET_WIDTH=10;
	private int frame;
	private boolean faceLeft=false;
	private float yOffset=0.1f;
	private float rotation;
	private float size;
	
	public FighterSprite(Texture texture, float size) {
		this.texture=texture;
		frame=1;
		this.size=size;
		
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setRotation(float rotation) {
		this.rotation = (float) Math.toDegrees(rotation);
	}
	
	public void setFaceLeft(boolean faceLeft) {
		this.faceLeft = faceLeft;
	}
	
	public void draw(SpriteBatch batch, Vector2 location)
	{
		float x=location.x;
		float y=location.y;
		float w=size;
		float h=size;
		float scaleX=1;
		float scaleY=1;
		int srcX=(frame%SPRITESET_WIDTH)*SPRITE_SIZE;
		int srcY=(frame/SPRITESET_WIDTH)*SPRITE_SIZE;
		int srcWidth=SPRITE_SIZE;
		int srcHeight=SPRITE_SIZE;
		boolean flipX=faceLeft;	
		float offset=yOffset*size;
		batch.draw(texture, x-w/2, y-h/2+offset, w/2, h/2-offset, w, h, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, false);
	}

	public boolean isFacingLeft() {
		return faceLeft;
	}
	
}
