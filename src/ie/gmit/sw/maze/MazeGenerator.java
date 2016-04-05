package ie.gmit.sw.maze;

public class MazeGenerator 
{
	private Node[][] maze;
	private int rows;
	private int cols;
	public MazeGenerator(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		buildMaze();
	}	
	public Node[][] getMaze()
	{
		return this.maze;
	}

	private void buildMaze()
	{
		RandomDepthFirstMazeGen rdfMaze = new RandomDepthFirstMazeGen(rows, cols);
		RecursiveBacktrackerMazeGen rbtMaze = new RecursiveBacktrackerMazeGen(rows, cols);
		BinaryTreeMazeGen btMaze = new BinaryTreeMazeGen(rows, cols);
		this.maze = rdfMaze.getMaze();
	}	
}