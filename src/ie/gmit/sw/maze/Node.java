package ie.gmit.sw.maze;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
public class Node 
{
	public enum Direction {North, South, East, West};
	private Node parent;
	private Color color = Color.BLACK;
	private Direction[] paths = null;
	public boolean visited =  false;
	public boolean isGoalNode;
	private int row = -1;
	private int col = -1;
	private int distance;
	private char nodeType;
	private Enemy e;
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public char getNodeType() {
		return nodeType;
	}

	public void setNodeType(char nodeType) {
		this.nodeType = nodeType;
	}
	
	public boolean isItem() {
		return nodeType == '?' || nodeType == 'W' || nodeType == 'G' || nodeType == 'B' || nodeType == 'H';
	}

	public boolean hasDirection(Direction direction){	
		for (int i = 0; i < paths.length; i++) {
			if (paths[i] == direction) return true;
		}
		return false;
	}
	
	public Node[] children(Node[][] maze){		
		java.util.List<Node> children = new java.util.ArrayList<Node>();
				
		if (row > 0 && maze[row - 1][col].hasDirection(Direction.South)) children.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1 && maze[row + 1][col].hasDirection(Direction.North)) children.add(maze[row + 1][col]); //Add South
		if (col > 0 && maze[row][col - 1].hasDirection(Direction.East)) children.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(Direction.West)) children.add(maze[row][col + 1]); //Add East
		
		return (Node[]) children.toArray(new Node[children.size()]);
	}

	public Node[] adjacentNodes(Node[][] maze){
		java.util.List<Node> adjacents = new java.util.ArrayList<Node>();
		
		if (row > 0) adjacents.add(maze[row - 1][col]);//Add North
		if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
		if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East

		return (Node[]) adjacents.toArray(new Node[adjacents.size()]);
	}
	
	public List<Node> getAdjacentNodes(Node[][] maze) {
		List<Node> adjacentNodes = new ArrayList<Node>();
		
		if(row-1 > 0) adjacentNodes.add(maze[row-1][col]); // Node Above
		if(row+1 < maze.length-1) adjacentNodes.add(maze[row+1][col]); // Node Below
		if(col-1 > 0) adjacentNodes.add(maze[row][col-1]); // Node to left
		if(col+1 < maze[0].length-1) adjacentNodes.add(maze[row][col+1]); // Node to right
		return adjacentNodes; 
	}
	
	public Direction[] getPaths() {
		return paths;
	}

	public void addPath(Direction direction) {
		int index = 0;
		if (paths == null){
			paths = new Direction[index + 1];		
		}else{
			index = paths.length;
			Direction[] temp = new Direction[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
		}
		
		paths[index] = direction;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return isGoalNode;
	}

	public void setGoalNode(boolean isGoalNode) {
		this.isGoalNode = isGoalNode;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		double d = 1;
		return (int)(d * Math.abs(x1 - x2) - Math.abs(y1-y2));
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}
	
	// is node passable
	public boolean isTraversable() {
		return nodeType == ' ' || nodeType == 'O' || nodeType == 'P';
	}

	public String toString() {
		return "[" + row + "/" + col + "]";
	}

	public void setEnemy(Enemy e) {
		this.e = e;
		
	}
	
	public Enemy getEnemy() {
		return e;
	}
	
	
}