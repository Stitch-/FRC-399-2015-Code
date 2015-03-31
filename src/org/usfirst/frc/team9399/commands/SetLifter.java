package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class SetLifter extends Command{
	SuperSystem ss;
	double speed;
	
	public SetLifter(double timeout,double speed){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
		this.speed=speed;
	}
	
	protected void initialize() {
		System.out.println("Setting Lifter");
		ss.funkyClips.setSpeed(speed);
	}
	
	protected void execute() {
		
	}
	
	protected void interrupted() {
		ss.funkyClips.setSpeed(0);
	}
	
	protected void end() {
		ss.funkyClips.setSpeed(0);
	}
	
	protected boolean isFinished() {	
		return this.isTimedOut()||(speed<0 && ss.funkyClips.getSwitch(true));
	}
}

