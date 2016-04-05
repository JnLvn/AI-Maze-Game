package ie.gmit.sw;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.maze.*;

public class GameView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private static final int IMAGE_COUNT = 10;
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Node maze[][];
	private Node goalNode;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	private boolean hintActive = false;
	private Player p;
	private Enemy e;
	private Item i;
	private Map<Node, Item> items = new HashMap<Node, Item>();
	
	public GameView(Node[][] maze) throws Exception{
		init();
		Node n = maze[currentRow][currentCol];
		p = new Player(n); // initialize player
		i = new Item(n); // initialize item
		goalNode = n;
		goalNode.setGoalNode(true);
		
		
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		initializeEnemies();
		initializeItems();
		timer.start();
	}
	
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = 'X';
       		
        		if (zoomOut){
        			ch = maze[row][col].getNodeType();
        			if (row == currentRow && col == currentCol){
        				g2.setColor(Color.BLUE);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			else if (maze[row][col].getNodeType() == 'G') {
        				g2.setColor(Color.YELLOW);
        				g2.fillRect(x1, y1, size, size);
        			}
        			else if (maze[row][col].getNodeType() == 'E') {
        				g2.setColor(Color.RED);
        				g2.fillRect(x1, y1, size, size);
        			}
        			else if (maze[row][col].getNodeType() == 'B') {
        				g2.setColor(Color.BLACK);
        				g2.fillRect(x1, y1, size, size);
        			}
        		}
        		else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
        		}
        		
        		
        		if (ch == 'X'){        			
        			imageIndex = 0;;
        		}else if (ch == 'W'){
        			imageIndex = 1;;
        		}else if (ch == '?'){
        			imageIndex = 2;;
        		}else if (ch == 'B'){
        			imageIndex = 3;;
        		}else if (ch == 'H'){
        			imageIndex = 4;;
        		}else if (ch == 'E'){
        			imageIndex = enemy_state;;
        		}else if (ch == 'P'){
        			imageIndex = 8;;	
        		}else if (ch == 'G'){
            			imageIndex = 7;;
        		}else if (ch == 'O'){
        			imageIndex = 9;;		
        		}else{
                		imageIndex = -1;
        		}
        		
        		if (imageIndex >= 0){
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}else{
        			g2.setColor(Color.LIGHT_GRAY);
        			g2.fillRect(x1, y1, size, size);
        		}      		
        	}
        }
	}
	
	public void toggleZoom()
	{
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) 
	{	
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		this.repaint();
	}
	
	private void initializeEnemies() throws Exception {
		for(int row=0; row<maze.length; row++) {
			for(int col=0; col<maze[0].length; col++) {
				if (maze[row][col].getNodeType() == 'E') {
					e = new EnemyImpl(maze, maze[row][col], this);
					e.setCurrentNode(maze[row][col]);
					enemies.add(e);
					maze[row][col].setEnemy(e);
				}
			}
		}
	}
	
	private void initializeItems() throws Exception {
		for(int row=0; row<maze.length; row++) {
			for(int col=0; col<maze[0].length; col++) {
				
				Node n = maze[row][col];
				
				// Initialize item objects
				if (n.isItem()) {
					Item i = new Item(n);
					items.put(n, i);
				}
			}
		}
	}
	
	public void updateEnemyPositions(Node current, Node next) {
		current.setNodeType(' ');
		next.setNodeType('E');
		
	}
	
	public void activateItem(Node n) {
		if (n.getNodeType() == 'W'){
			p.addWeapon();
			System.out.println("Weapon added Level = "+ p.getWeaponLevel()); 
		}
		else if (n.getNodeType() == 'B'){
			System.out.println("Bomb");
		}
		else if (n.getNodeType() == 'H'){
			System.out.println("H-Bomb");
		}
		else if (n.getNodeType() == '?'){
			i = items.get(n);
		    i.activateItem(maze, p.getCurrentNode(), goalNode, this);
		}
		else if (n.getNodeType() == 'G'){
			System.out.println("Congrats You Escaped The Maze");
	    	System.exit(DEFAULT_VIEW_SIZE); // exit from game
		}
	    else{
	    	System.out.println("Node empty");
	    }
		
	}
	
	// Hint reveals path to goal node.
	public void showPath(List<Node> path) {

		if (hintActive) p.setStepCount(0);
		for(Node node : path) {
			if (node.getNodeType() != 'P' &&
					node.getNodeType() != 'E' && node.getNodeType()!= 'G') 
				node.setNodeType('O');
		}
		hintActive = true;
		System.out.println("Show Path");
	}
	 // Turn hint off after number of steps
	public void hidePath(List<Node> path) {
		if (hintActive) {
			hintActive = false;
			for(Node p : path) {
				if (p.getNodeType() == 'O') p.setNodeType(' ');
			}
		}
	}
	
	public void initializeFight(Node enemyNode) {
		
		Enemy battle = null;
		for(Enemy e : enemies) {
			if (e.getCurrentNode() == enemyNode) battle = e;
		}
		System.out.println("Now get fuzzy stuff");
		int damage = new Fight().fuzzyFight(battle, p.getWeaponLevel());
		
		p.setWeaponLevel(p.getWeaponLevel()-5);
		p.setHealth(p.getHealth() - damage);
		System.out.println("Player-Health = " + p.getHealth());
		if(p.getHealth() <= 0)
		{
			System.out.println("You are Dead, Game Over");
	    	System.exit(DEFAULT_VIEW_SIZE); // exit from game
		}
	}
	
	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/hedge.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));		
		images[2] = ImageIO.read(new java.io.File("resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("resources/door.jpg"));
		images[8] = ImageIO.read(new java.io.File("resources/cptCillian.png"));
		images[9] = ImageIO.read(new java.io.File("resources/wayOut.png"));
	}
	
	
	
	
	
}