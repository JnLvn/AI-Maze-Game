package ie.gmit.sw.maze;


public class BinaryTreeMazeGen extends AbstractMazeGenerator
{
	private Node [][] maze;
	public BinaryTreeMazeGen(int rows, int cols) 
	{
		super(rows, cols);
		inn();
		buildMaze();
		
		int featureNumber = (int)((rows * cols) * 0.01);	
		addFeature('W', 'X', 100);
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

	@Override
	public void buildMaze()
	{
		this.maze = super.getMaze();
		
		for (int row = 1; row < maze.length -1 ; row ++){
			for (int col = 1; col < maze[row].length -1; col+=2){
				int num = (int) (Math.random() * 10);
				if (col > 0 && (num >= 5)){
					maze[row][col].addPath(Node.Direction.West);
					maze[row - 1][col].setNodeType(' ');
				}
				else{
					maze[row][col].addPath(Node.Direction.North);
					maze[row][col - 1].setNodeType(' ');
				}
			}
		}
		super.setGoalNode();
		super.setMaze(this.maze);
	}
	
	
	public Node[][] getMaze()
	{
		return this.maze;
	}
}