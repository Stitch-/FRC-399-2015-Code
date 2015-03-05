package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class GrabSRCAuton extends CommandGroup {
	public GrabSRCAuton(){
		boolean[] talonRetract={false,false};
		boolean[] talonExtend={true,true};
		boolean[] wingCommand={true,false,false,true};
		boolean[] wingRetract={false,true,false,true};
		addSequential(new SetStartingAngle(180));
		addParallel(new SetTalons(1,talonExtend));
		addSequential(new SetWings(1,wingCommand));
		addSequential(new SetTalons(0.2,talonRetract));
		//addSequential(new DriveStraight(2,-8,1,false));
		addSequential(new DriveStraight(5,50,1,true));
		addSequential(new TurnToAngle(1,-90));
		//double[] heading={0,0.1,-0.5};
		//addSequential(new DriveHeading(0.7,heading));
		addSequential(new SetTalons(1,talonRetract));
		addSequential(new SetWings(0.5,wingRetract));
	}
	
}