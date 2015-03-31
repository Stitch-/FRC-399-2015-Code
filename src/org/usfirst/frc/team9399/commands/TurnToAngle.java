package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
import org.usfirst.frc.team9399.systems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command{
	SuperSystem ss;
	double ang;
	int initState;
	
	public TurnToAngle(double timeout,double ang){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
		this.ang=ang;
		
	}
	
	protected void initialize() {
		System.out.println("Turning to Angle: "+ang);
		initState=ss.drivetrain.getState();
		ss.drivetrain.setState(DriveTrain.states.FIELD_CENTRIC_W_GYRO_HOLD);
		ss.drivetrain.setYawTarget(ang);
	}
	protected void execute() {
		System.out.println(ss.drivetrain.getPidVal());
		
	}
	protected void interrupted() {
		ss.drivetrain.setState(initState);
	}
	protected void end() {
		ss.drivetrain.setState(initState);
	}
	protected boolean isFinished() {
		//double val = ss.drivetrain.getPidVal();
		return this.isTimedOut(); // || (val<0.09 && val>-0.09);
	}
}
