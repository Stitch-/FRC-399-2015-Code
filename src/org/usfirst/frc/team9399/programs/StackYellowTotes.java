package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackYellowTotes extends CommandGroup {
	
	public StackYellowTotes(){
		addSequential(new ResetLifter(1));
		addSequential(new SetLifter(1,1));
	
	
}
}