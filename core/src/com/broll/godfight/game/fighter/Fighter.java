package com.broll.godfight.game.fighter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.broll.godfight.damage.AttackDamage;
import com.broll.godfight.fighter.FighterData;
import com.broll.godfight.fighter.FighterTeam;
import com.broll.godfight.game.GameEnvironment;
import com.broll.godfight.input.InputLayout;
import com.broll.godfight.physic.bodies.PlayerBody;
import com.broll.godfight.resource.FighterSprite;
import com.broll.godfight.stage.StageLocation;
import com.broll.godfight.utils.Vector4;

public class Fighter {

	private final static float PERCENT_DIVIDEND = 50;
	private GameEnvironment game;
	private FighterData data;
	private InputLayout input;
	private FighterSprite sprite;
	private PlayerBody playerBody;
	private MovementController movement;
	private MovesetController moveset;
	private FighterTeam team;
	private FighterAttributes attributes;

	public Fighter(GameEnvironment game, FighterData data, InputLayout input, FighterTeam team, int stocks) {
		this.data = data;
		this.input = input;
		this.game = game;
		this.team = team;
		attributes = new FighterAttributes(stocks);
		sprite = new FighterSprite(game.getResource().getTexture(data.getFighterSpriteset()), data.getSpriteHeight());
		moveset = new MovesetController(game, this);
		movement = new MovementController(game, this);
	}

	public void kill() {
		attributes.useStock();
		attributes.setOnStage(false);
		if (attributes.stocksLeft()) {
			// start respawn timer
			attributes.initRespawnTime();
		}
	}

	public void hit(Vector2 hitLocation, AttackDamage damage) {
		float percentDamage = attributes.getPercent();
		float percent = damage.getDamage();
		float power = damage.getPower() * Math.max(1, (percentDamage / PERCENT_DIVIDEND));
		if (power > data.getBalance()) {
			// flung away
			movement.throwBody(hitLocation, damage.getImpulseVector(), power);
		} else {
			// normal hit
			movement.hitBody(hitLocation, damage.getImpulseVector(), power);
		}
		// do damge
		attributes.doDamage(percent);
	}

	public void spawnOnStage() {
		attributes.setOnStage(true);
		StageLocation spawn = game.getStage().getStartLocation(this);
		if (playerBody == null) {
			// create body at spawn position
			playerBody = new PlayerBody(this, new Vector4(spawn.getLocation().x, spawn.getLocation().y, data.getWidth(),
					data.getHeight()));
			game.getPhysic().addBody(playerBody);
			movement.init();
		} else {
			// just place to spwan position
			playerBody.reset();
			playerBody.teleport(spawn.getLocation());
		}
		sprite.setFaceLeft(spawn.isFaceLeft());
		playerBody.updateParameters(data.getWeight(), data.getWidth(), data.getHeight());
	}

	public void update(float delta) {
		if (attributes.isOnStage()) {
			if (!moveset.moveRunning()) {
				movement.update(delta);
			}
			if (!playerBody.isFlung()) {
				moveset.update(delta);
			}
			playerBody.update(delta);
			sprite.setRotation(playerBody.getBody().getAngle());
		} else {
			if (attributes.stocksLeft()) {
				attributes.updateRespawnTime(delta);
				if (attributes.respawnTimeDone()) {
					attributes.respawn();
					spawnOnStage();
				}
			}
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch, playerBody.getBody().getPosition());
	}

	public FighterSprite getSprite() {
		return sprite;
	}

	public PlayerBody getPlayerBody() {
		return playerBody;
	}

	public InputLayout getInput() {
		return input;
	}

	public FighterData getData() {
		return data;
	}

	public MovesetController getMoveset() {
		return moveset;
	}

	public MovementController getMovement() {
		return movement;
	}

	public FighterTeam getTeam() {
		return team;
	}

	public FighterAttributes getAttributes() {
		return attributes;
	}
}
