package model;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.math.*;
import javax.vecmath.*;
import control.Help;

public class TankV2 {
        public float xSpeed, ySpeed, x, y, direction, angleChange, velocity;
        //static final int LENGTH = 20;
        private Animation anim;
        private Help h;
        
        
        public TankV2(int x, int y){
        		h = new Help();
        		anim = new Animation();
        		anim.addFrame(h.loadPic("images/tankbase2.png"), 500);
                angleChange = 0;
                xSpeed = ySpeed = 0;
                this.x = x;
                this.y = y;
                direction = 0;
        }
       
        public void accelerate(boolean f, double delta){
        	
    		float newV = velocity;
    		newV += 0.2f * (float) delta;
    		this.velocity = newV;
        	if (f) {
                xSpeed += Math.sin(direction)*velocity;
                ySpeed += Math.cos(direction)*velocity;
                x += xSpeed;
                y += ySpeed;
        	} else {
                xSpeed -= Math.sin(direction)*velocity;
                ySpeed -= Math.cos(direction)*velocity;
                x += xSpeed;
                y += ySpeed;
        	}
        	
        	System.out.println(velocity);

        }
       
        public void draw(Graphics2D g){

                direction += angleChange * 0.005f;//(Math.sqrt(Math.pow(xSpeed, 2)+Math.pow(ySpeed, 2)))*0.01;//*div(Math.abs(velocity),(velocity));
             
                if (angleChange > 1){
                        angleChange = 1;
                }else if (angleChange < -1){
                        angleChange = -1;
                }
                
                xSpeed *= 0.7;
                ySpeed *= 0.7;

                velocity = 0f;
                angleChange *= 0;
                
                g.setColor(Color.black);
                g.drawLine((int)(x), (int)(y), (int)(x-(20)*Math.sin(direction)), (int)(y-(20)*Math.cos(direction)));
                
        		int xx = (int) (x + this.anim.getImage().getWidth(null) / 2);
        		int yy = (int) (y + this.anim.getImage().getHeight(null) / 2);
        		
        		AffineTransform orig = new AffineTransform();
        		g.rotate(-direction,xx, yy);

        		g.drawImage(anim.getImage(),(int)x,(int)y, null);
        		g.setColor(Color.red);
        		
        		g.rotate(direction, xx, yy);
        		g.setTransform(orig);
         
        }
}

