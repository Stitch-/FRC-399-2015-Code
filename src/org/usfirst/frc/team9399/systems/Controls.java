package org.usfirst.frc.team9399.systems;

//import org.usfirst.frc.team9399.util.SubSystem;
import edu.wpi.first.wpilibj.Joystick;


public class Controls /*implements SubSystem*/{
	Joystick left;
	Joystick right;
	final int resetButton = 1;
	
	public Controls(int leftPort, int rightPort){
		left=new Joystick(leftPort);
		right=new Joystick(rightPort);
	}
	
	public double[] getHeading(){
		double x=left.getRawAxis(0);
		double y=left.getRawAxis(1);
		double z=right.getRawAxis(2);
		double[] heading = {-x,y,-z};
		return heading;
	}
	
	public boolean isResetPressed(){
		boolean left,right,out;
		left=this.left.getRawButton(resetButton);
		right=this.right.getRawButton(resetButton);
		out=left && right;
		return out;
	}
}