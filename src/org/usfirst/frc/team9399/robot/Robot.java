package org.usfirst.frc.team9399.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team9399.systems.*;
import org.usfirst.frc.team9399.util.FileLogger;

public class Robot extends IterativeRobot {
	
	SuperSystem ss;
	int driveState = DriveTrain.states.FIELD_CENTRIC; //choose whether to drive with the gyroscope, mainly for troubleshooting.
	FileLogger captainsLog = new FileLogger("/home/lvuser/logs/"); //Intstantiate's Justin's file logger. 
															//all console outputs will be saved to a log.
	
	public void robotInit() {
		ss = new SuperSystem();
    }

	
	public void autonomousInit() {
		ss.drivetrain.setState(driveState);
	}
    
    public void autonomousPeriodic(){

    }

    public void teleopInit() {
    	ss.drivetrain.setState(driveState);
    	
    }
   
    public void teleopPeriodic(){
    	double[] heading=ss.control.getHeading();
    	ss.drivetrain.setHeading(heading);
    	ss.drivetrain.run();
    	if(ss.control.isResetPressed() ){ // Reset the gyroscope when both triggers are pressed.
    		ss.drivetrain.setState(DriveTrain.states.RESET_FIELD_REF);
    	}else if(ss.drivetrain.getState() != driveState){
    		ss.drivetrain.setState(driveState);
    	}
    }
    
    
    public void disabledInit() {
    	ss.drivetrain.setState(DriveTrain.states.DISABLED);
    }
    
}
