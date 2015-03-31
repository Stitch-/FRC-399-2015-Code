package org.usfirst.frc.team9399.programs;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team9399.commands.*;

public class StackYellowTotes2 extends CommandGroup {

	public StackYellowTotes2(){
		addSequential(new SetStartingAngle(-120));//set starting angle 270*
			//grab rc
		addSequential(new ResetLifter(200));//reset lifter
		addSequential(new SetGrabber(0.5,true));//actuate grabber
		addSequential(new SetLifter(1,1));//elevate lifter
		addSequential(new SetIntakeActuators(0.5,false));//open the intake
		addSequential(new TurnToAngle(1,-90)); //Turn to align with the tote
		//drive forward
		//grab tote
			//lower lifter (reset?)
			//elevate lifter
		//spin 180*
		//drive forward
		//grab tote
			//lower lifter (reset?)
			//elevate lifter
		//strafe to port
		//drive forward
		//strafe to starboard
		//drive forward
		//grab tote
			//lower lifter
	
	}
	
	
}
