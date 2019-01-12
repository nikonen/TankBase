package control;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Cannon;
import model.Tank;
import model.TankV2;

public class Loop extends Canvas implements KeyListener,
		MouseMotionListener, MouseListener {

	private BufferStrategy buffer;
	private boolean gameRunning;
	private TankV2 tank;
	private Cannon cannon;
	private int mx, my;
	boolean up, down, right, left, moving, turbo, aim = false;
	public float CHANGE = 0.9f;

	public Loop() {

		tank = new TankV2(100,100);
		cannon = new Cannon();
		JFrame frame = new JFrame();
		frame.setTitle("Co-op");
		frame.setSize(new Dimension(1024, 768));
		JPanel panel = (JPanel) frame.getContentPane();
		panel.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		createBufferStrategy(2);
		buffer = getBufferStrategy();
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		requestFocus();
		this.gameRunning = true;
		gameLoop();

	}

	public void gameLoop() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;


		while (gameRunning) {

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			doGameUpdates(delta);

			render();
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (Exception e) {
			}

		}
	}

	private void doGameUpdates(double delta) {
		
		cannon.setPosition(tank.x + 10 , tank.y + 20);
		
		if (up) {
			tank.velocity += CHANGE;
			tank.accelerate(true, delta);
		}
	
		if (left) {
			tank.angleChange += 3f;
		}
		if (right) {
			tank.angleChange -= 3f;
		}
		if (down) {
			tank.velocity = CHANGE;
			tank.accelerate(false, delta);
		}
		
		if (turbo) {
			CHANGE = 1.5f;
		} else {
			CHANGE = 0.9f;
		}
		
		
			cannon.setDirTest(mx, my);
		
	}

	public void render() {
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.fillRect(0, 0, 1024, 768);
		tank.draw(g);
		cannon.draw(g);
		g.setColor(Color.red);
		g.setFont(new Font("Dialog", Font.BOLD, 14));
		g.drawString("Direction: " + tank.direction,  10, 30);
		g.drawString("Xspeed: " +tank.xSpeed, 10, 50);
		g.drawString("Yspeed: " +tank.ySpeed, 10, 70);
		g.dispose();
		buffer.show();
	}

	public static void main(String[] args) {

		new Loop();

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mx = arg0.getPoint().x;
		my = arg0.getPoint().y;

	}
	public void keyPressed(KeyEvent arg0) {

		int code = arg0.getKeyCode();

		switch (code) {
		case KeyEvent.VK_W:
			up = true;
			break;
			
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
			
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_T:
			turbo = true;
			break;
			
		case KeyEvent.VK_R:
			cannon.resetDir();
			break;
		}

	}

	public void keyReleased(KeyEvent arg0) {

		int code = arg0.getKeyCode();

		switch (code) {
		case KeyEvent.VK_W:
			up = false;
			break;
			
		case KeyEvent.VK_A:
			left = false;
			break;
			
		case KeyEvent.VK_D:
			right = false;

		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_T:
			turbo = false;
			break;

		}
		

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3) {
			aim = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 3) {
			aim = false;
		}
		
	}

}
