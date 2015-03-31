package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Test extends CommandGroup {
    
    public Test() {
    	addSequential(new SetIntakeActuators(1,true));
    }
}
