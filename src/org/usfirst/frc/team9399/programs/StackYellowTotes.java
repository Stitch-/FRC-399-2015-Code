package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackYellowTotes extends CommandGroup {
	
	public StackYellowTotes(){
		//                        x,y,z
		double[] strafeHeading = {-1, 0, 0};
		double[] negatingHeading = {1,0,0};
		addSequential(new ResetLifter(1));
		addSequential(new SetStartingAngle(210)); 
		addParallel(new SetGrabber(.5,true));
		addSequential(new DriveStraight(.5,.5,1,true));//Drive straight a second
		addSequential(new SetGrabber(.5, false));
		addSequential(new SetLifter(.5,1));//drive the lifter for the RC
		addSequential(new TurnToAngle(.5,270));//turn angle for the tote
		addSequential(new DriveStraight(.5,1,1,true));//Drive up to the tote or straight
		addSequential(new SetIntake(.5,1));//run the intake
		addSequential(new SetLifter(.5, -1));
		addSequential(new SetLifter(.5,1));//run the lifter for the tote
		addSequential(new DriveHeading(.5, strafeHeading));//turn 90ish degrees
		addSequential(new DriveStraight(2,20,1,false));//drive straight
		addSequential(new DriveHeading(.5, negatingHeading));//turn so you are pushing the RC away from tote
		addSequential(new DriveStraight(2,.5,1,true));//drive into the tote #2
		addSequential(new SetIntake(.5,1));//run the intake
		addSequential(new SetLifter(.5,-1));
		addSequential(new SetLifter(.5,1));//run the lifter for #2 tote
		addSequential(new DriveHeading(.5, strafeHeading));//turn 90ish degrees
		addSequential(new DriveStraight(.5,.5,1,true));//drive straight
		addSequential(new DriveHeading(.5,negatingHeading));//turn so you are pushing the RC away from tote
		addSequential(new DriveStraight(.5,.5,1,true));//drive into the tote #3
		addSequential(new SetIntake(.5,1));//run the intake
		addSequential(new SetLifter(.5,-1));
		addSequential(new SetLifter(.5,1));//run the lifter for #3 tote
	}
}