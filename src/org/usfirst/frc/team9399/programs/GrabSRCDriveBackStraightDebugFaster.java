package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabSRCDriveBackStraightDebugFaster extends CommandGroup {
	public GrabSRCDriveBackStraightDebugFaster(){
		boolean[] talonEngage={false,false};
		boolean[] talonDisengage={true,true};
		boolean[] wingCommand={true,false,false,true};
		boolean[] wingRetract={false,true,true,false};
		
		addSequential(new SetStartingAngle(180));		
		addParallel(new SetTalons(0.9,talonDisengage));
		//addSequential(new Wait(0.1));		//addSequential(new DriveStraight(2,-9,1,false));
		addSequential(new SetWings(0.9,wingCommand));
		addSequential(new Wait(0.1)); //0.5 //0.25 //redue to 0.1 as bouncing is 'fixed'
		addSequential(new SetTalons(0.2,talonEngage)); //make these parallel
		addParallel(new DriveStraight(2,40,1)); //27 //40,1 //50,0.7
		addSequential(new Wait(0.25));//0.1 //may reduce based on time it takes for rcs to settle
		addParallel(new SetTalons(1,talonDisengage)); //these too
		addSequential(new DriveStraight(1,20,0.7));
		addSequential(new SetWings(1,wingRetract));

	}
	
}
