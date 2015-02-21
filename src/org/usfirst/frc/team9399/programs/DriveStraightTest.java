package org.usfirst.frc.team9399.programs;

import org.usfirst.frc.team9399.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveStraightTest extends CommandGroup {
    
    public  DriveStraightTest() {
    	addSequential(new DriveStraight(1000000,14,1,false));

        
    }
}
