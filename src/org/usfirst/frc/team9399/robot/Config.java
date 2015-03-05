package org.usfirst.frc.team9399.robot;

public abstract class Config {

	public static class DriveTrain{
		public static final int[] MOTOR_PORTS={8,3,9,4}; //fl.fr.bl.br
		public static final int[] ENC_PORTS={4,5,0,1,8,9,6,7};
		
		public static final double[] GYRO_PID={0.01,0.0001,0};
		public static final double[] WHEEL_PID={0,0,0};
		
		public static final int GYRO_FAIL_THRESHOLD=50;
		public static final double TICKS_TO_INCHES = 0.0209439;
		/*public static final int FRONT_RIGHT=1;
		public static final int FRONT_LEFT=6;
		public static final int REAR_RIGHT=4;
		public static final int REAR_LEFT=9;*/
		
		/*public static final double P_TUNING = 0.0075;
		public static final double I_TUNING = 0;
		public static final double D_TUNING = 0;
		public static final double P_WHEEL = 0;
		public static final double I_WHEEL = 0;
		public static final double D_WHEEL = 0;*/
	}
	
	public static class Controls{
		public static final int[] CONTROL_PORTS={0,1,2}; //L:0,R:1,OP:2
		public static final double DEADBAND=0.1;
		
		/*
		public static final int JOY_LEFT=0;
		public static final int JOY_RIGHT=1;
		public static final int OPERATOR_PAD=2;
		*/
	}
	
	public class KeyMap{ //keymap for op pad.
		public static final int TOGGLE_LEFT_WING=4;
		public static final int TOGGLE_RIGHT_WING=3;
		public static final int TOGGLE_CLAW=2;
		public static final int TOGGLE_RIGHT_CLAW=3;
		public static final int TOGGLE_LEFT_CLAW=1;
		public static final int INTAKE_AXIS=1;
		public static final int LIFTER_AXIS=1;
	}
	public static class Pneumatics{
		public static final int COMP_ID=0;
	}
	
	public static class Wings{
		public static final int[] MOTOR_PORTS={6,1}; //l:0,r:1
		public static final int[] SOL_PORTS={3,1};
		public static final int SWITCH_THRESHOLD=0xff0;
		public static final double MOTOR_SPEED=1;
		public static final int[] BUTTON_PORTS={22,23,21,20};//0 is left, 2 is right, odds are for extended
		
		/*
		public static final int LEFT_SOL=3;
		public static final int RIGHT_SOL=1;
		
		public static final int LEFT_MOTOR=6;
		public static final int RIGHT_MOTOR=1;
		*/
	}
	
	public static class Lifter{
		public static final double LEAD_SCREW_CONSTANT=0.15; //inches from the top of on thread to the top of the thread below it
		public static final int[] MOTOR_PORTS={2,7};
		public static final int[] LIMIT_SWITCH_PORTS={3,2,0};
		public static final int SOL_PORT=2;
		public static final int[] ENCODER_PORTS={2,0};
		public static final int ENCODER_TURNS=100;
		public static final int MAX_HEIGHT=12; //inches
		public static final int SWITCH_THRESHOLD=200;
		public static final double DEADBAND=0.05;
		public static final int[] PDP_TERMINALS={2,13};
		public static final double[] PID={0,0,0};
	}
	
	public static class Intake{
		public static final int[] MOTOR_PORTS={5,0}; //L:0,R:1
		public static final double SPEED_MULT=1;
		/*
		public static final int LEFT_PORT=5;
		public static final int RIGHT_PORT=0;
		*/
	}
}
