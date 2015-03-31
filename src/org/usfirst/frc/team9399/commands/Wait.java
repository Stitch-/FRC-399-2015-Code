package org.usfirst.frc.team9399.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

	public Wait(double time){
		setTimeout(time);
	}
	
	public void initialize(){
		
	}
	
	public void execute(){
		
	}
	
	public void interrupted(){
		
	}
	
	public void end(){
		
	}
	
	public boolean isFinished(){
		return isTimedOut();
	}
	
}