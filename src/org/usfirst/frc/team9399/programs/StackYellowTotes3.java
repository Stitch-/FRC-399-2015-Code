package org.usfirst.frc.team9399.programs;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team9399.commands.*;

public class StackYellowTotes3 extends CommandGroup {
	
	public StackYellowTotes3(){
		addSequential(new SetStartingAngle(210));
		
		//addParallel(new SetIntakeActuators(0.5,true));
		addParallel(new SetIntake(1,-1));
		double[] heading={0,0.5,0.1};
		addSequential(new DriveHeading(1,heading));
		
		addParallel(new SetIntake(2,-1));
		addSequential(new SetIntakeActuators(2,true));
		
		addParallel(new SetIntake(1,-1));

		addSequential(new DriveStraight(1,3,1)); //1,10,1
		
		addParallel(new SetIntake(0.5,-1));
		addSequential(new Wait(0.5));
		
		addParallel(new SetIntake(1,-1));
		addSequential(new DriveStraight(1,-3,1)); //1,-10,1
		
		addSequential(new SetIntake(1,-1)); //timeout: 2
		
		addParallel(new SetIntakeActuators(2,false));
		addSequential(new ResetLifter(2));
		//addSequential(new SetIntakeActuators(2,false)); //commented out to see if I could push rc
		//addSequential(new SetLifter(1,1));
		addParallel(new SetGrabber(1,true));
		addParallel(new SetLifter(1.1,1));
		addSequential(new TurnToAngle(1,270));
		
		double[] heading2={0.65,0,0.01}; //{0.6,0.2,0}; //0.6,0,0 //0.01
		addSequential(new DriveHeading(1,heading2));
		
		addSequential(new DriveStraight(1,12,1)); //1,15,1;
		
		double[] heading3={-0.8,0,0}; //{-0.9,0.2,0}; //0.9.0.1,0 //-0.01
		//addParallel(new SetIntake(1,-1));
		addSequential(new DriveHeading(1.7,heading3));
		
		//double[] heading4={0,0.5,0.1};
		//addParallel(new SetIntake(1,-1));
		//addSequential(new DriveStraight(1,10,1));
		//addSequential(new DriveHeading(1,heading4));
		
		//double[] heading5={0.4,0,0}; //0.5,0,0
		//addSequential(new TurnToAngle(1,270));
		//addSequential(new SetLifter(1.2,1));
		//addSequential(new DriveHeading(1,heading5));
		
		addSequential(new TurnToAngle(0.5,270));
		
		//addParallel(new SetLifter(1,1));
		addParallel(new SetIntake(1,-1));
		addSequential(new DriveStraight(1,13,1));
		
		addSequential(new SetIntakeActuators(1,true));
		
		addParallel(new SetIntake(1,-1));
		addSequential(new DriveStraight(1,-2,1));
		
		//addParallel(new SetIntakeActuators(2,false));
		addSequential(new ResetLifter(3));
		
		addParallel(new SetIntakeActuators(1,false));
		addSequential(new SetLifter(1,1));
		
		double[] heading6={0.8,0,0};
		addSequential(new DriveHeading(1.1,heading6));
		
		addSequential(new DriveStraight(1,13,1));
		
		double[] heading7={-0.9,0,0};
		addSequential(new DriveHeading(1,heading7));
		
		addSequential(new TurnToAngle(1,260));
		
		addParallel(new SetIntake(1,-1));
		addSequential(new DriveStraight(1,20,1));
		
		addParallel(new SetIntakeActuators(1,true));
		addParallel(new SetIntake(1,-1));
		addSequential(new TurnToAngle(1,310));
		
		addSequential(new DriveStraight(1,30,1));
		

		addSequential(new ResetLifter(2));
		
		
		addParallel(new SetIntakeActuators(1,false));
		addSequential(new DriveStraight(1,-10,0.8));
		/*
		addParallel(SetIntake(1,1));
		addSequential(new DriveForward(1,5,1));
		
		addParallel(new SetIntakeActuators(1,true);
		addParallel(new SetIntake(1,1)):
		addSequential(new TurnToAngle(1,355);
		
		addSequential(
		
		
		*/
	}

}

