package com.broll.godfight.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.objects.Football;
import com.broll.godfight.objects.MovingPlatform;
import com.broll.godfight.objects.RotatingPost;
import com.broll.godfight.objects.WoodBox;
import com.broll.godfight.physic.bodies.Platform;
import com.broll.godfight.physic.bodies.StaticRectangle;
import com.broll.godfight.physic.bodies.StaticTriangle;
import com.broll.godfight.resource.ResourceManager;
import com.broll.godfight.utils.Vector4;

public class TestStage extends Stage {



	private Texture background;

	private Sprite rockSprite;

	public TestStage(GameEnvironment game) {
		super(game);
	}

	@Override
	public void buildStage() {
		ResourceManager rm = game.getResource();

		background = rm.getTexture("fogcastle_background.jpg");
		Texture stone = rm.getTexture("stone_texture.jpg");


		
		for (int i = 0; i < 50; i++) {
			game.getObjects().addObject(new WoodBox(game, new Vector2(5, 50+i)));
		}
		for (int i = 0; i < 35; i++) {
			game.getObjects().addObject(new Football(game, new Vector2((float) (-5+Math.random()*20), 10+i)));
		}
		game.getObjects().addObject(new RotatingPost(game, new Vector2(5, 7), new Vector2(4,0.5f), new Vector2(7,0.25f)));
		RotatingPost rp2=new RotatingPost(game, new Vector2(8, 7.5f), new Vector2(3,0.5f), new Vector2(5,0.25f));
		game.getObjects().addObject(rp2);
		rp2.setRotationSpeed(-0.5f);
		game.getObjects().addObject(new RotatingPost(game, new Vector2(8.5f, 6.5f), new Vector2(4,0.5f), new Vector2(7,0.25f)));
		
		Vector2[] waypoints= new Vector2[]{new Vector2(-5, 2),new Vector2(0, 2),new Vector2(5, 15)};
		MovingPlatform platform=new MovingPlatform(game, new Vector2(3, 2), 1,waypoints);
		game.getObjects().addObject(platform);

		addRender(addBody(new StaticRectangle(new Vector4(-10, 3, 5, 2))), stone, 200);

		addRender(addBody(new StaticRectangle(new Vector4(0, 0, 15, 5))), stone, 200);
		addRender(addBody(new StaticTriangle(new Vector4(-7, -1, 7, 3), true)), stone, 200);
		addRender(addBody(new StaticTriangle(new Vector4(-7, 10, 7, 6), false)), stone, 200);
		addRender(addBody(new StaticTriangle(new Vector4(17, 10, 7, 6), false)), stone, 200);

		addRender(addBody(new StaticRectangle(new Vector4(15, -3f, 10, 5))), stone, 200);
		addRender(addBody(new StaticRectangle(new Vector4(21.5f, 1, 2, 1f))), stone, 200);

		
	

		Platform p = new Platform(new Vector3(8, 3, 4));
		addBody(p);
		addRender(new PolygonRender(rm.getTexture("mossy_mountain.jpg"), p.getBounds(3), p.getPolygonVertices(3), 100));

		rockSprite = new Sprite(rm.getTexture("rock.png"));
		rockSprite.setBounds(8.8f, -2.3f, 4, 3);
		
	

	}



	@Override
	public StageLocation getStartLocation(Fighter fighter) {
		return new StageLocation(new Vector2(5, 12), false);
	}

	@Override
	public void renderForeground() {
		Batch batch = game.getContainer().batch;
		rockSprite.draw(batch);

	}

	@Override
	public void renderBackground() {
		SpriteBatch batch = game.getContainer().batch;
		float x = bounds.x;
		float w = bounds.z - bounds.x;
		float h = bounds.w - bounds.y;
		float y = bounds.y + h;
		h *= -1;

		float wp = 20;
		float hp = 15;
		x -= wp;
		w += wp * 2;
		y -= hp;
		h += hp * 2;
		batch.draw(background, x, y, w, h);

		renderStageRenders();
	}

	boolean moveUp = true;

	@Override
	public void update(float delta) {

		
	}

}
