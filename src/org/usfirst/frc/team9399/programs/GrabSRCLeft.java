package org.usfirst.frc.team9399.programs;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team9399.commands.*;

public class GrabSRCLeft extends CommandGroup {
	public GrabSRCLeft(){
		boolean[] talonRetract={false,true};
		boolean[] talonExtend={true,true};
		boolean[] wingCommand={true,true};
		boolean[] wingRetract={true,false};
		addSequential(new SetStartingAngle(180));
		addParallel(new SetTalons(1,talonExtend));
		addSequential(new SetWings(1,wingCommand));
		addSequential(new SetTalons(0.2,talonRetract));
		addSequential(new DriveStraight(2,60,1,true));
		addSequential(new SetWings(1,wingRetract));
	}
}
