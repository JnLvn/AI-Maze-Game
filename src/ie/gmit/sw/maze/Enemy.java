package ie.gmit.sw.maze;

import ie.gmit.sw.GameView;


public interface Enemy {

	public void search(Node[][] maze, Node startNode, GameView g);
	
	public void setMaze(Node[][] maze); 
	
	public void setCurrentNode(Node currentNode);	public Node getCurrentNode();
	public void setNextNode(Node nextNode); public Node getNextNode();

	public int getStrength();
}
