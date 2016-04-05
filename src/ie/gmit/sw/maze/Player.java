package ie.gmit.sw.maze;

import java.util.concurrent.ThreadLocalRandom;

public class Player {

	private Node currentNode;
	private int stepCount = 0; // for the AStar path activated by the hint item
	private Weapon weapon = null;
	private int health = 20; // health can be adjusted to make it harder or easier
	
	public Player(Node currentNode) {
		this.currentNode = currentNode;
	}
	
	public Node getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNode(Node currentNode) {
		if(this.currentNode != currentNode)	stepCount++;
		this.currentNode = currentNode;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getStepCount() {
		return stepCount;
	}

	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}
	
	public void addWeapon() {
		int wLevel = ThreadLocalRandom.current().nextInt(3, 9);
		if (weapon == null || weapon.getLevel() < wLevel)	weapon = new Weapon(wLevel);
	}
	
	public int getWeaponLevel() {
		if (weapon!= null)	return weapon.getLevel();
		else return 0;
	}
	
	public void setWeaponLevel(int level) {
		if (weapon != null) weapon.setLevel(level);
	}
	
	
}
