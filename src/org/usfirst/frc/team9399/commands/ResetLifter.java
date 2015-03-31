package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;


public class ResetLifter extends Command {
	SuperSystem ss;
	double speed=0.5;
	
	public ResetLifter(double timeout,double speed){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
		this.speed=speed;
	}
	
	public ResetLifter(double timeout){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
	}
	
	protected void initialize() {
		System.out.println("Reseting lifter to lowest position");
		ss.funkyClips.setSpeed(-Math.abs(speed));
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
		//System.out.println(ss.funkyClips.getSwitch(true));
		return this.isTimedOut()||ss.funkyClips.getSwitch(true);
	}
}

