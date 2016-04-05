package ie.gmit.sw.maze;

import ie.gmit.sw.*;
import ie.gmit.sw.traversers.*;

public class Item {

	private Node node;
	
	public Item(Node node) {
		this.node = node;
	}
	
	public void activateItem(Node[][] maze, Node startNode, Node goal, GameView g) {
		
		if (node.getNodeType() == '?') {
			
			new AStar(maze, startNode, goal, g);
			System.out.println("AStar hint");
		}
		
	}
	
	
}
