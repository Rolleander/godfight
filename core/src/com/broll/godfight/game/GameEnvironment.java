package com.broll.godfight.game;

import com.broll.godfight.GodFightGame;
import com.broll.godfight.damage.DamageManager;
import com.broll.godfight.game.fighter.Fighter;
import com.broll.godfight.objects.ObjectManager;
import com.broll.godfight.particles.ParticlesManager;
import com.broll.godfight.physic.GamePhysic;
import com.broll.godfight.resource.ResourceManager;
import com.broll.godfight.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

	private GodFightGame container;
	private ResourceManager resource;
	private GamePhysic physic;
	private Stage stage;
	private List<Fighter> fighters=new ArrayList<Fighter>();
	private DamageManager damage;
	private ParticlesManager particles;
	private StageObserver stageObserver;
	private ObjectManager objects;
	
	public GameEnvironment(GodFightGame container,ResourceManager resource) {
		this.resource=resource;
		this.container=container;
		damage=new DamageManager(this);
		particles=new ParticlesManager(this);
		stageObserver=new StageObserver(this);
		objects=new ObjectManager(this);
	}
	
	public void init(Stage stage){
		this.stage=stage;
		physic=new GamePhysic(this);
		stage.buildStage();
		stage.addBodies(physic);
		stageObserver.init();
	}
	
	public void addFighter(Fighter fighter){
		fighter.spawnOnStage();
		fighters.add(fighter);
	}
	
	public void dispose(){
		
	}
	
	public GamePhysic getPhysic() {
		return physic;
	}
	
	public ResourceManager getResource() {
		return resource;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public List<Fighter> getFighters() {
		return fighters;
	}
	
	public GodFightGame getContainer() {
		return container;
	}
	
	public DamageManager getDamage() {
		return damage;
	}
	
	public ParticlesManager getParticles() {
		return particles;
	}
	
	public StageObserver getStageObserver() {
		return stageObserver;
	}
	
	public ObjectManager getObjects() {
		return objects;
	}
}
