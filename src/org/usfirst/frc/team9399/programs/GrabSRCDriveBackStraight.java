package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabSRCDriveBackStraight extends CommandGroup {
	public GrabSRCDriveBackStraight(){
		boolean[] talonEngage={false,false};
		boolean[] talonDisengage={true,true};
		boolean[] wingCommand={true,false,false,true};
		boolean[] wingRetract={false,true,true,false};
		
		addSequential(new SetStartingAngle(180));		
		addParallel(new SetTalons(0.9,talonDisengage));
		//addSequential(new DriveStraight(2,-9,1,false));
		addSequential(new SetWings(0.9,wingCommand));
		addSequential(new SetTalons(0.2,talonEngage));
		addSequential(new DriveStraight(5,27,1,true));
		addSequential(new SetTalons(1,talonDisengage));
		addSequential(new SetWings(1,wingRetract));

	}
	
}
