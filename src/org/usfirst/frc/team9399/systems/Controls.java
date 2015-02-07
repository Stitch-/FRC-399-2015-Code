package org.usfirst.frc.team9399.systems;

//import org.usfirst.frc.team9399.util.SubSystem;
import edu.wpi.first.wpilibj.Joystick;

public class Controls /*extends SubSystem*/{
	Joystick left,right,op;
	final int resetButton = 1;
	double band;
	
	public Controls(int leftPort, int rightPort,int opPort,double deadband){
		left=new Joystick(leftPort);
		right=new Joystick(rightPort);
		op=new Joystick(opPort);
		band = deadband;
	}
	
	public double[] getHeading(){
		double x=left.getRawAxis(0);
		double y=left.getRawAxis(1);
		double z=right.getRawAxis(2)*0.2;
		double[] heading = {x,-y,z};
		for(int i=0;i<2;i++){
			heading[i]=deadBand(heading[i]);
		}
		return heading;
	}
	
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
	
	public double[] getOpPadRight(){
		double x=op.getRawAxis(2);
		double y=op.getRawAxis(3);
		double[] out={x,y};
		return out;
	}
	
	public double[] getOpPadLeft(){
		double x=op.getRawAxis(0);
		double y=op.getRawAxis(1);
		double[] out={x,y};
		return out;
	}
	
	public class pads{
		public static final int LEFT=0;
		public static final int RIGHT=1;
		public static final int OP=2;
	}
	
	public boolean getButton(int c, int button){
		boolean out;
		if(c==pads.LEFT){
			out=left.getRawButton(button);
		}else if(c==pads.RIGHT){
			out=right.getRawButton(button);
		}else{
			out=op.getRawButton(button);
		}
		return out;
	}
	
	public boolean isResetPressed(){
		boolean left,right,out;
		left=this.left.getRawButton(resetButton);
		right=this.right.getRawButton(resetButton);
		out=left && right;
		return out;
	}
	
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
