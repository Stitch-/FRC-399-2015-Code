package org.usfirst.frc.team9399.systems;

//import org.usfirst.frc.team9399.util.SubSystem;
import edu.wpi.first.wpilibj.Joystick;

public class Controls /*extends SubSystem*/{
	public Joystick left,right,op; //input devices
	final int resetButton; //the id of the button on both joysticks that is used to reset the gyro 
	double band; //the deadband value for driver joysticks
	
	/**Values representing the different input devices*/
	public class pads{
		public static final int LEFT=0;
		public static final int RIGHT=1;
		public static final int OP=2;
	}
	 
	/**
	 * Class to manage inputs from joysticks and gamepad
	 * @author Aaron Elersich (aelersich1@gmail.com)
	 * @param ports
	 * @param deadband
	 */
	
	public Controls(int[] ports,double deadband,int reset){
		left=new Joystick(ports[0]);
		right=new Joystick(ports[1]);
		op=new Joystick(ports[2]);
		band = deadband;
		resetButton=reset;
	}
	
	/**Get driver heading*/
	public double[] getHeading(){
		double x=left.getRawAxis(0);
		double y=left.getRawAxis(1);
		double z=right.getRawAxis(2)*0.1;
		double[] heading = {x,-y,z};
		for(int i=0;i<2;i++){
			heading[i]=deadBand(heading[i]);
		}
		return heading;
	}
	
	/**get the heading in tank drive*/
	public double[] getHeadingTank(){
		double l=-left.getRawAxis(1);
		double r=-right.getRawAxis(1);
		double drive = (l+r) /2.0;
		double strafe = 0;
		double rotate = 0.5 * (l-r) /2.0;
		double[] out = {strafe,drive, rotate};
		
		for(int i=0;i<2;i++){
			out[i]=deadBand(out[i]);
		}

		//System.out.println("l " +l + " r " + r + " d " + drive + " s " + strafe + " r " + rotate);
		
		return out;
	}
	
	/**get the right op pad joystick*/
	public double[] getOpPadRight(){
		double x=op.getRawAxis(2);
		double y=op.getRawAxis(3);
		double[] out={x,y};
		return out;
	}
	
	/**get the left op pad joystick*/
	public double[] getOpPadLeft(){
		double x=op.getRawAxis(0);
		double y=op.getRawAxis(1);
		double[] out={x,y};
		return out;
	}
	
	/**get pov up/down for intake*/
	public double getOpPadPOV(){
		int pov=op.getPOV();
		double out=0;
		if(pov!=-1){
			if(pov==0){
				out=1;
			}else if(pov==180){
				out=-1;
			}
		}
		return out*op.getPOVCount();
	}
	
	/**get a button value on a specified device*/
	public boolean getButton(int device, int button){
		boolean out;
		if(device==pads.LEFT){
			out=left.getRawButton(button);
		}else if(device==pads.RIGHT){
			out=right.getRawButton(button);
		}else{
			out=op.getRawButton(button);
		}
		return out;
	}
	
	/**Are both reset buttons pressed?*/
	public boolean isResetPressed(){
		boolean left,right,out;
		left=this.left.getRawButton(resetButton);
		right=this.right.getRawButton(resetButton);
		out=left && right;
		return out;
	}
	
	/**adjust values according to the deadband*/
	double deadBand(double val){
		double out;
		if(val<band&&val>-band){
			out=0;
		}else{
			out=val;
		}
		return out;
	}
}
