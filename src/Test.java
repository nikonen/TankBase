
import javax.swing.JOptionPane;	
public class Test {

	    public static void main(String[] args) {

	        double g = 0;

	        double velocity = 0;
	        double position = 0;
	        double time = 0;
	        double totalTime = 0;
	        double dt = 0;
	        double lastTime = System.nanoTime()*1E-9;

	        int counter = 0;

	        // input acceleration
	        String accelerationString = JOptionPane.showInputDialog("input acceleration");
	        g = Double.parseDouble(accelerationString);

	        while(counter < 20000)
	        {
	           // calculate dt
	           time = System.nanoTime()*1E-9;
	           dt =  time - lastTime;
	           lastTime = time;
	          totalTime = totalTime + dt;

	           // calculate the new velocity
	           velocity = velocity + g * dt;
	           // calculate the new position
	           position = position + velocity * dt;

	            System.out.println("fallen " + position + "m velocity = " + velocity +  "m/s over " + totalTime);
	           
	           counter++;
	        }
	    }
	}
