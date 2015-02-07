package org.usfirst.frc.team399.robot;

import org.usfirst.frc.team399.systems.*;
import org.usfirst.frc.team399.util.Toggler;

public class SuperSystem {
	public Controls control;
	public DriveTrain drivetrain;
	//public Pneumatics compressor;
	public Wings wingeyBits;
	public Lifter funkyClips;
	public Intake sucker;
	Toggler[] t=new Toggler[5];
	
	public SuperSystem(){
		control = new Controls(Config.Controls.JOY_LEFT,Config.Controls.JOY_RIGHT,Config.Controls.OPERATOR_PAD,
				Config.Controls.DEADBAND);
		
		drivetrain = new DriveTrain(Config.DriveTrain.PORTS,Config.DriveTrain.ENC_PORTS);
		drivetrain.setState(DriveTrain.states.DISABLED);
		drivetrain.initPid(Config.DriveTrain.GYRO_PID,Config.DriveTrain.WHEEL_PID);
		
		//compressor=new Pneumatics(Config.Pneumatics.COMP_ID);
		
		wingeyBits=new Wings(Config.Wings.LEFT_MOTOR,Config.Wings.RIGHT_MOTOR,Config.Wings.LEFT_SOL,Config.Wings.RIGHT_SOL,
				Config.Wings.SWITCH_THRESHOLD,Config.Pneumatics.COMP_ID,Config.Wings.MOTOR_SPEED,Config.Wings.BUTTON_PORTS);
		wingeyBits.setState(Wings.states.RETRACTED);
		
		funkyClips=new Lifter(Config.Lifter.MOTOR_PORTS,Config.Lifter.SOL_PORTS,Config.Lifter.ENCODER_PORTS,
				Config.Lifter.LEAD_SCREW_CONSTANT,Config.Lifter.ENCODER_TURNS,Config.Lifter.MAX_HEIGHT); 
		funkyClips.setState(Lifter.states.BRAKE);
		
		sucker=new Intake(Config.Intake.leftPort,Config.Intake.rightPort);
		//instantiate all the things
	}
	
	public boolean[] toggleControls(){
		boolean[] out=new boolean[5];
		boolean[] buttons=new boolean[5];
    	buttons[0] = control.getButton(Controls.pads.OP, Config.KeyMap.TOGGLE_LEFT_WING);
    	buttons[1] = control.getButton(Controls.pads.OP, Config.KeyMap.TOGGLE_RIGHT_WING);
    	buttons[2] = control.getButton(Controls.pads.OP, Config.KeyMap.TOGGLE_CLAW);
    	buttons[3] = control.getButton(Controls.pads.OP, Config.KeyMap.TOGGLE_LEFT_CLAW);
    	buttons[4] = control.getButton(Controls.pads.OP, Config.KeyMap.TOGGLE_RIGHT_CLAW);
		for(int i=0;i<5;i++){
			t[i].set(buttons[i]);
			out[i]=t[i].get();
		}
		return out;
	}
	
	
}
