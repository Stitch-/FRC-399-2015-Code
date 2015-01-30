package org.usfirst.frc.team9399.robot;

import org.usfirst.frc.team9399.systems.Controls;
import org.usfirst.frc.team9399.systems.DriveTrain;

public class SuperSystem {
	public Controls control;
	public DriveTrain drivetrain;
	
	public SuperSystem(){
		control = new Controls(Config.Controls.JOY_LEFT,Config.Controls.JOY_RIGHT,Config.Controls.DEADBAND);
		drivetrain = new DriveTrain(Config.DriveTrain.FRONT_LEFT,Config.DriveTrain.FRONT_RIGHT,
				Config.DriveTrain.REAR_LEFT,Config.DriveTrain.REAR_RIGHT,Config.DriveTrain.GYRO_PORT
				,Config.DriveTrain.GYRO_SENSE);
		drivetrain.setState(DriveTrain.states.DISABLED);
		drivetrain.initPid(Config.DriveTrain.P_TUNING, Config.DriveTrain.I_TUNING, Config.DriveTrain.D_TUNING);
		
	}
	
	
}
