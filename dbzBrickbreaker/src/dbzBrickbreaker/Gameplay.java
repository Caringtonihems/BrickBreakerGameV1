package dbzBrickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	private boolean start = false;
	private int score = 0;
	
	private int totalbricks = 21;
	
	private Timer timer;
	private int delay = 3;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int balldirX = -1;
	private int balldirY = -2;
	
	private Mapgenerator map;
	
	public Gameplay()
	{
		map = new Mapgenerator(3,7);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g)
	{
		//set the background color
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//draw the map
		map.draw((Graphics2D)g);
		
		//set the game borders 
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(681, 0, 3, 592);
		
		//score setup
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		
		//paddle design
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		
		//design the ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalbricks <= 0)
		{
			start = false;
			balldirX = 0;
			balldirY = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won, Final Score"+score, 190,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter To Play Again", 230,350);
		}
		
		if(ballposY > 570)
		{
			start = false;
			balldirX = 0;
			balldirY = 0;
			
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("GAMEOVER, Final Score"+score, 190,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter To Play Again", 230,350);
		}
		
		g.dispose();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub	
		timer.start();
		
		if(start)
		{
			
			if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550,100, 8)))
			{
				balldirY = -balldirY; 
			}
			
		A: 	for(int i = 0; i<map.map.length; i++)
			{
				for(int j = 0; j<map.map[0].length; j++)
				{
					if(map.map[i][j]>0)
					{
						int brickX = j* map.brickwidth + 80;
						int brickY = i* map.brickheight + 50;
						int brickWidth = map.brickwidth;
						int brickHeight = map.brickheight;
						
						Rectangle rect = new Rectangle (brickX,brickY,brickWidth,brickHeight);
						Rectangle ballrect = new Rectangle (ballposX, ballposY, 20,20);
						Rectangle brickRect = rect;
						
						if(ballrect.intersects(brickRect))
						{
							map.setBrickValue(0, i, j);
							totalbricks--;
							score +=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
							{
								balldirX = -(balldirX) ;
							}
							else
							{
								balldirY = -(balldirY);
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += balldirX;
			ballposY += balldirY;
			
			if(ballposX < 0 )
			{
				balldirX = -balldirX;
			}
			
			if(ballposY < 0 )
			{
				balldirY = -balldirY;
			}
			
			if(ballposX > 670 )
			{
				balldirX = -balldirX;
			}
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
		{
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
		}
		
		if(e.getKeyCode()== KeyEvent.VK_LEFT)
		{
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
		}
		if(e.getKeyCode()== KeyEvent.VK_ENTER)
		{
			if(!start)
			{
				start = true;
				ballposX = 120;
				ballposY = 350;
				balldirX = -1;
				balldirY = -2;
				playerX = 310;
				score = 0;
				totalbricks = 21;
				map = new Mapgenerator (3,7);
				
				repaint();
			}
		}
	}
	
	
	public void moveRight()
	{
		start = true;
		playerX += 20;
	}
	
	public void moveLeft()
	{
		start = true;
		playerX -=20;
	}
	

	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}

}
