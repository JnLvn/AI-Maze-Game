package ie.gmit.sw;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import ie.gmit.sw.traversers.*;
import ie.gmit.sw.maze.*;


public class Runner implements KeyListener
{
	private static final int MAZE_DIMENSION = 50;
	private Node[][] model;
	private int currentRow;
	private int currentCol;
	private int enemyCount;
	private GameView view;
	
	public Runner() throws Exception
	{	
		MazeGenerator m = new MazeGenerator(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
    	view = new GameView(model);
    	
    	placePlayer();
    	System.out.println("Start");
    	/*for(enemyCount = 0; enemyCount <= 20; enemyCount++){     // different way i was going to add enemies
    	placeEnemy();
    	}*/
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
	}
	
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol].setNodeType('P');
    	updateView(); 		
	}
	
	/*private void placeEnemy(){  
		
		boolean isGround = false;
		
		while(!isGround)
		{
			currentRow = (int) (MAZE_DIMENSION * Math.random());
			currentCol = (int) (MAZE_DIMENSION * Math.random());
			if(model[currentRow][currentCol].getNodeType() == ' ')
			{
				isGround = true;
			}
		}
    	model[currentRow][currentCol].setNodeType('E'); 
		
	}*/
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}


    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) {
        		currentCol++;   		
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) {
        		currentCol--;	
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)){
    			currentRow--;
    		}
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) {
    			currentRow++;        	  		
    		}
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }else if (e.getKeyCode() == KeyEvent.VK_C){
        	inspectItem(currentRow, currentCol);
        }else{
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int r, int c)
	{
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == ' ' || model[r][c].getNodeType() == 'E')
		{
			// player and enemy meet, fight!!!
			if (model[r][c].getNodeType() == 'E') view.initializeFight(model[r][c]);
			model[currentRow][currentCol].setNodeType(' ');
			model[r][c].setNodeType('P');
			view.repaint();
			return true;
		}
		else
		{
			// inspect wall for items
			inspectItem(r, c);
			return false; //Can't move
		}
	}
	
	private void inspectItem(int r, int c) {
		Node n = model[r][c];
		
		if(n.isItem()) {
			 view.activateItem(n);
			n.setNodeType('X'); // replace item taken from the wall with hedge image
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Runner();
	}
	
}
