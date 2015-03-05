package org.usfirst.frc.team9399.programs;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team9399.commands.*;

public class GrabSRCRight extends CommandGroup {
	public GrabSRCRight(){
		boolean[] talonRetract={true,false};
		boolean[] talonExtend={true,true};
		boolean[] wingCommand={false,false,false,true};
		boolean[] wingRetract={false,false,true};
		addSequential(new SetStartingAngle(180));
		addParallel(new SetTalons(1,talonExtend));
		addSequential(new SetWings(1,wingCommand));
		addSequential(new SetTalons(0.2,talonRetract));
		addSequential(new DriveStraight(2,60,1,true));
		addSequential(new SetWings(1,wingRetract));
	}
}
