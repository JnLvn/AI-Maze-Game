package ie.gmit.sw.traversers;

import ie.gmit.sw.maze.*;

public interface Traversator {

	public void traverse(Node startNode);
	
	public void setMaze(Node[][] maze); 
	public Node getCurrentNode();
	public Node getNextNode();
	public void setCurrentNode(Node currentNode); 
	public void setNextNode(Node nextNode);

	public void setFinished(boolean b); 
}
