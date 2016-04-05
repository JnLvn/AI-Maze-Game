package ie.gmit.sw.maze;

import ie.gmit.sw.*;
import ie.gmit.sw.traversers.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyImpl implements Enemy {

	private Traversator t;
	private Random r = new Random(); 
	private int strength;
	private static int minStrength = 1;
	private static int maxStrength = 9;
	
	public EnemyImpl(Node[][] maze, Node startNode, GameView g) throws Exception  {
		search(maze, startNode, g);
		strength = ThreadLocalRandom.current().nextInt(minStrength, maxStrength);
	}
	
	public void search(Node[][] maze, Node startNode, GameView g) {
		int i = r.nextInt(1);
		
		if(i == 0) t = new RandWlk(maze, startNode, g); 
	}
	
	public void setMaze(Node[][] maze) {
		t.setMaze(maze);
	}
	
	public Node getCurrentNode() {
		return t.getCurrentNode();
	}
	
	public void setCurrentNode(Node currentNode) {
		t.setCurrentNode(currentNode);
	}
	
	public void setNextNode(Node nextNode) {
		t.setNextNode(nextNode);
	}

	public Node getNextNode() {
		return t.getNextNode();
	}
	
	public int getStrength() {
		return this.strength;
	}
	
}
