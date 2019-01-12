package control;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Cannon;
import model.Tank;

public class OtherLoop extends Canvas implements KeyListener,
		MouseMotionListener {

	private BufferStrategy buffer;
	private boolean gameRunning;
	private float gravity = 0.01f;
	private Tank tank;
	private Cannon cannon;
	private int mx, my;
	boolean up, down, right, left, moving = false;

	public OtherLoop() {

		tank = new Tank();
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
		requestFocus();
		this.gameRunning = true;
		gameLoop();

	}

	public void gameLoop() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		// keep looping round til the game ends
		while (gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			// update the game logic
			doGameUpdates(delta);
			// draw everyting
			render();

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in
			// ns.

			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (Exception e) {
			}

		}
	}

	private void doGameUpdates(double delta) {

		ArrayList<Tank> stuff = new ArrayList<Tank>();
		
		cannon.setPosition(tank.getX() + 20 , tank.getY()+10);
		cannon.setDirTest(mx,my);

		if (up) {
			tank.move(delta, true);
			tank.setMoving(true);
		}

		if (down) {
			tank.move(delta, false);
			//tank.setVelocity(-tank.getVelocity());
			tank.setMoving(true);
		}
		
		if (!down && !up) {
			tank.setVelocity(0);
		}
		
		if (right) {
			tank.redAngle();
		}

		if (left) {
			tank.incrAngle();
		}

		for (int i = 0; i < stuff.size(); i++) {
			// all time-related values must be multiplied by delta!
			// Stuff s = stuff.get(i);
			// s.velocity += gravity * delta;
			// s.position += s.velocity * delta;

			// stuff that isn't time-related doesn't care about delta...
			if (tank.velocity >= 1000) {
				// s.color = Color.RED;
			} else {
				// s.color = Color.BLUE;
			}
		}
	}

	public void render() {
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.fillRect(0, 0, 1024, 768);
		tank.draw(g);
		cannon.draw(g);
		g.dispose();
		buffer.show();
	}

	public static void main(String[] args) {

		new OtherLoop();

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

		}
		

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
