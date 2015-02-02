package org.usfirst.frc.team9399.robot;

import org.usfirst.frc.team9399.systems.*;
import org.usfirst.frc.team9399.util.Toggler;

public class SuperSystem {
	public Controls control;
	public DriveTrain drivetrain;
	//public Pneumatics compressor;
	public Wings wingeyBits;
	public Lifter lifter;
	Toggler[] t=new Toggler[5];
	
	public SuperSystem(){
		control = new Controls(Config.Controls.JOY_LEFT,Config.Controls.JOY_RIGHT,Config.Controls.OPERATOR_PAD,
				Config.Controls.DEADBAND);
		
		drivetrain = new DriveTrain(Config.DriveTrain.FRONT_LEFT,Config.DriveTrain.FRONT_RIGHT,
				Config.DriveTrain.REAR_LEFT,Config.DriveTrain.REAR_RIGHT);
		drivetrain.setState(DriveTrain.states.DISABLED);
		drivetrain.initPid(Config.DriveTrain.P_TUNING, Config.DriveTrain.I_TUNING, Config.DriveTrain.D_TUNING);
		
		//compressor=new Pneumatics(Config.Pneumatics.COMP_ID);
		
		wingeyBits=new Wings(Config.Wings.LEFT_MOTOR,Config.Wings.RIGHT_MOTOR,Config.Wings.LEFT_SOL,Config.Wings.RIGHT_SOL,
				Config.Wings.SWITCH_THRESHOLD,Config.Pneumatics.COMP_ID,Config.Wings.MOTOR_SPEED,Config.Wings.BUTTON_PORTS);
		wingeyBits.setState(Wings.states.RETRACTED);
		
		lifter=new Lifter(Config.Lifter.MOTOR_PORTS,Config.Lifter.SOL_PORTS,Config.Lifter.ENCODER_PORTS,
				Config.Lifter.LEAD_SCREW_CONSTANT,Config.Lifter.ENCODER_TURNS,Config.Lifter.MAX_HEIGHT); 
		lifter.setState(Lifter.states.BRAKE);
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
