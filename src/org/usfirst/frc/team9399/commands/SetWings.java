package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class SetWings extends Command{
	SuperSystem ss;
	boolean[] commandVals;
	final boolean[] shutOff={false,false,false,false};
	
	public SetWings(double timeout,boolean[] in){
		setTimeout(timeout);
		ss=SuperSystem.getInstance();
		commandVals=in;
	}
	
	protected void initialize() {
		System.out.println("Running wings");
		ss.wingeyBits.setCommand(commandVals);
	}
	protected void execute() {

	}
	protected void interrupted() {
		ss.wingeyBits.setCommand(shutOff);
	}
	protected void end() {
		ss.wingeyBits.setCommand(shutOff);
	}
	protected boolean isFinished() {	
		return this.isTimedOut();
	}
}
