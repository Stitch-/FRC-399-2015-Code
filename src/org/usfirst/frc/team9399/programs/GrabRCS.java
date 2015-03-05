package org.usfirst.frc.team9399.programs;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team9399.commands.*;

public class GrabRCS extends CommandGroup {
	public GrabRCS(){
		addParallel(new SetGrabber(1,true));
		addSequential(new ResetLifter(3));
		/*addParallel(new SetGrabber(1,false)); //opens claw
		addSequential(new DriveStraight(1,30,1,true)); //drives forward
		addSequential(new SetGrabber(1,true)); //grabs can
		addParallel(new SetLifter(1,1));
		addSequential(new DriveStraight(1,-30,1,true)); //backs up*/
	}
	
}
