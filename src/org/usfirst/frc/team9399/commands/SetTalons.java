package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
import org.usfirst.frc.team9399.systems.Wings;

import edu.wpi.first.wpilibj.command.Command;

public class SetTalons extends Command{
	SuperSystem ss;
	boolean[] commandVals;
	boolean done=false;
	
	public SetTalons(boolean[] in){
		ss=SuperSystem.getInstance();
		commandVals=in;
	}
	
	public SetTalons(double timeout,boolean[] in){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
		commandVals=in;
	}
	
	protected void initialize() {
		System.out.println("Runnning Talons");
		ss.wingeyBits.actuateHook(Wings.wings.LEFT,commandVals[0]);
		ss.wingeyBits.actuateHook(Wings.wings.RIGHT,commandVals[1]);
		done=true;
	}
	protected void execute() {
		
	}
	protected void interrupted() {
		
	}
	protected void end() {
		
	}
	protected boolean isFinished() {	
		return this.isTimedOut()||done;
	}
}
