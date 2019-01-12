package model;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.math.*;

import control.Help;

public class CannonV2 {
        public float x, y, direction, angleChange, velocity;
        //static final int LENGTH = 20;
        private Animation anim;
        private Help h;
        
        public CannonV2(int x, int y){
        		h = new Help();
        		anim = new Animation();
        		anim.addFrame(h.loadPic("images/tankcannon.png"), 500);
                angleChange = 0;
                this.x = x;
                this.y = y;
                direction = 0;
        }
        
    	public void setDirTest(float xx, float yy) {
    		
    		float rx = xx - this.x;
    		float ry = yy - this.y;
    		
    		float currentAngle = (float)Math.atan2(ry, rx);
    		float diff = this.direction - currentAngle;
    		if(diff < 0){ diff += (float)Math.PI * 2; }

    		if(diff < Math.PI){
    		    this.direction-=0.02f;
    		}else if (diff > Math.PI){
    			this.direction+=0.02f;
    		} else {
    			this.direction = 0;
    		}
    	}
    	
    	public void resetDir() {
    		this.direction = 0;
    	}

        public void draw(Graphics2D g){

                direction += angleChange * 0.005f;//(Math.sqrt(Math.pow(xSpeed, 2)+Math.pow(ySpeed, 2)))*0.01;//*div(Math.abs(velocity),(velocity));
             
                if (angleChange > 1){
                        angleChange = 1;
                }else if (angleChange < -1){
                        angleChange = -1;
                }

                angleChange *= 0;
                
                g.setColor(Color.black);
                g.drawLine((int)(x), (int)(y), (int)(x-(20)*Math.sin(direction)), (int)(y-(20)*Math.cos(direction)));
                
        		int xx = (int) (x + this.anim.getImage().getWidth(null) / 2);
        		int yy = (int) (y + this.anim.getImage().getHeight(null) / 2);
        		
        		g.drawString("ANGLE: "+this.direction, 0, 90);
        		
        		AffineTransform orig = new AffineTransform();
        		g.rotate(direction,xx, yy);

        		g.drawImage(anim.getImage(),(int)x,(int)y, null);
        		g.setColor(Color.red);
        		
        		g.rotate(-direction, xx, yy);
        		g.setTransform(orig);
        		
        		
         
        }
}

