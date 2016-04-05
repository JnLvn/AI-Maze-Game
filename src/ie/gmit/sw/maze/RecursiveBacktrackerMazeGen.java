package ie.gmit.sw.maze;

import java.util.Random;
import ie.gmit.sw.maze.Node.*;

public class RecursiveBacktrackerMazeGen extends AbstractMazeGenerator
{
	private Node [][] maze;
	public RecursiveBacktrackerMazeGen(int rows, int cols) 
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
		addFeature('G', 'X', featureNumber);
		
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
	
	public void buildMaze()
	{
		this.maze = super.getMaze();
		
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		Node node = maze[randRow][randCol];
		
		carve(node);
	}
	
	private void carve(Node node){
		node.setVisited(true);
		
		Node[] adjacents = node.adjacentNodes(super.getMaze());
		super.shuffle(adjacents);
		
		for (int i = 0; i < adjacents.length; i++) {
			if (!adjacents[i].isVisited()){
				Direction dir = getDirection(node, adjacents[i]);
				node.addPath(dir);
				node.setNodeType(' ');
				adjacents[i].addPath(opposite(dir));
				carve(adjacents[i]);
			}
		}
		
	}

}
