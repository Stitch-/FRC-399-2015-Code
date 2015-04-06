package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabSRCDriveBackStraightDebug extends CommandGroup {
	public GrabSRCDriveBackStraightDebug(){
		boolean[] talonEngage={false,false};
		boolean[] talonDisengage={true,true};
		boolean[] wingCommand={true,false,false,true};
		boolean[] wingRetract={false,true,true,false};
		
		addSequential(new SetStartingAngle(180));		
		addParallel(new SetTalons(0.9,talonDisengage));
		//addSequential(new Wait(0.1));		//addSequential(new DriveStraight(2,-9,1,false));
		addSequential(new SetWings(0.9,wingCommand));
		addSequential(new Wait(0.25)); //0.5
		addSequential(new SetTalons(0.2,talonEngage));
		addSequential(new DriveStraight(2,40,1)); //27 //40,1 //50,0.7
		addSequential(new Wait(0.25));
		addSequential(new SetTalons(1,talonDisengage));
		addSequential(new DriveStraight(1,20,0.7));
		addSequential(new SetWings(1,wingRetract));

	}
	
}
