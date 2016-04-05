package ie.gmit.sw.maze;

import ie.gmit.sw.maze.Node.*;

import java.util.Random;
import java.util.*;

public class RandomDepthFirstMazeGen extends AbstractMazeGenerator {
	private Node [][] maze;
	public RandomDepthFirstMazeGen(int rows, int cols) 
	{
		super(rows, cols);
		inn();
		buildMaze();
		
		int featureNumber = (int)((rows * cols) * 0.01);	
		addFeature('W', 'X', 200);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
		addFeature('E', 'X', featureNumber);
		addFeature('G', 'X', 1);
	}
	
	private void inn()
	{
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col].setNodeType('X');
			}
		}
	}

	private void addFeature(char feature, char replace, int number)
	{
		int counter = 0;
		while (counter < number)
		{
			int row = (int)(maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getNodeType() != ' ')
			{
				maze[row][col].setNodeType(feature);
				counter++;
			}
		}
	}
	
	
	//Generates Mazes with a low branching factor and high a "river" factor because the algorithm explores as far as possible along each branch before backtracking.
		public void buildMaze(){
			this.maze = super.getMaze();
			
			Random generator = new Random();
			int randRow = generator.nextInt(maze.length);
			int randCol = generator.nextInt(maze[0].length);
			Node node = maze[randRow][randCol];
			node.setNodeType(' ');
			
			Deque<Node> stack = new LinkedList<Node>();
			stack.addFirst(node);
			
			while (!stack.isEmpty()){
				node = stack.poll();
				node.setVisited(true);
				
				Node[] adjacents = node.adjacentNodes(maze);
				super.shuffle(adjacents);
				
				for (int i = 0; i < adjacents.length; i++) {
					if (!adjacents[i].isVisited()){
						stack.addFirst(adjacents[i]);
						Direction dir = getDirection(node, adjacents[i]);
						node.addPath(dir);
						node.setNodeType(' ');
						adjacents[i].addPath(opposite(dir));
						adjacents[i].setVisited(true);
					}
				}
			}
		}
	
		
	

}

