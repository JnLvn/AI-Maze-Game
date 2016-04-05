package ie.gmit.sw.traversers;

import ie.gmit.sw.maze.*;
import ie.gmit.sw.*;

import java.util.*;
import java.util.PriorityQueue;

public class AStar implements Traversator {

private Node goal;
private GameView g;
private Node[][] maze;
	
	public AStar(Node[][] maze, Node start, Node goal, GameView g){
		this.maze = maze;
		System.out.println("Goal Node: " + goal);
		this.goal = goal;
		this.g = g;
		traverse(maze, goal);
	}
	
	public void traverse(Node[][] maze, Node node) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		       // TraversatorStats.printStats(node, time, visitCount);
		        List<Node> path = new ArrayList<Node>();
				
				while (node.getParent() != null) {
					path.add(node);
					System.out.println("Paths added");
					node = node.getParent();
				}
				// return List of Nodes, repaint the scene 
				// show path to goal
				System.out.println("Traverse Show Path");
				g.showPath(path);
				
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Process adjacent nodes
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);
				
				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
			}									
		}
	}

	@Override
	public void traverse(Node startNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaze(Node[][] maze) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getCurrentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getNextNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentNode(Node currentNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextNode(Node nextNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFinished(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}
