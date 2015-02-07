package org.usfirst.frc.team399.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

//import edu.wpi.first.wpilibj.smartdashboard.*;


import org.usfirst.frc.team399.systems.*;
import org.usfirst.frc.team399.util.FileLogger;
import org.usfirst.frc.team399.util.Toggler;


public class Robot extends IterativeRobot {
	
	SuperSystem ss; //class that contains all subsystems. Mainly for instantiation of subsystems.
	static final int mode = DriveTrain.states.FIELD_CENTRIC;
	int driveState = mode; //choose whether to drive with the gyroscope and/or pid, mainly for troubleshooting.
	boolean[] padButtons;
	boolean tank;
	Toggler tankDrive=new Toggler();
	Toggler turbo=new Toggler();
	Toggler roboCent=new Toggler();
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
    	//ss.compressor.setState(Pneumatics.states.ENABLED);
    	ss.funkyClips.setState(Lifter.states.BRAKE);
    }
   
    public void teleopPeriodic(){
    	boolean toggleTank =  ss.control.getButton(Controls.pads.RIGHT, 12);
    	boolean toggleTurbo = ss.control.getButton(Controls.pads.LEFT, 12);
    	boolean toggleCentric = ss.control.getButton(Controls.pads.RIGHT, 15);
    	tankDrive.set(toggleTank);
    	turbo.set(toggleTurbo);
    	roboCent.set(toggleCentric);
   
    	double[] heading;
   
    	if(tankDrive.get()){
    		driveState=DriveTrain.states.TANK_DRIVE;
    		heading = ss.control.getHeadingTank();
    	}else{
    		if(roboCent.get()){
    			driveState=DriveTrain.states.ROBOT_CENTRIC;
    		}else{
        		driveState=mode;    			
    		}
    		heading=ss.control.getHeading();
    	}
    	
    	ss.sucker.setState(Intake.states.ACTIVE);
    	
    	if(turbo.get()){
    		ss.drivetrain.setTurbo(true);
    	}else{
    		ss.drivetrain.setTurbo(false);
    	}
    	
    	//System.out.println(heading[0]+ " "+heading[1]);
    	/*padButtons=ss.toggleControls();
    	
    	ss.wingeyBits.setState(Wings.wings.LEFT,padButtons[0]?0:1);
    	ss.wingeyBits.setState(Wings.wings.RIGHT,padButtons[1]?0:1);
    	ss.lifter.actuateClaw(padButtons[2]);
    	ss.wingeyBits.actuateHook(Wings.wings.LEFT,padButtons[3]);
    	ss.wingeyBits.actuateHook(Wings.wings.RIGHT,padButtons[4]);
    	*/
    	ss.drivetrain.setHeading(heading);
    	ss.sucker.setWheels(ss.control.getOpPadLeft()[Config.KeyMap.INTAKE_AXIS]);
    	ss.funkyClips.setSpeed(ss.control.getOpPadRight()[Config.KeyMap.LIFTER_AXIS]);
    	ss.drivetrain.run();
    	ss.sucker.run();
    	/*ss.compressor.run();
    	ss.wingeyBits.run();*/
    	ss.funkyClips.run();
    	if(ss.control.isResetPressed() ){ // Reset the gyroscope when both triggers are pressed.
    		ss.drivetrain.setState(DriveTrain.states.RESET_FIELD_REF);
    	}else if(ss.drivetrain.getState() != driveState){
    		ss.drivetrain.setState(driveState);
    	}
    }
    
    public void disabledInit() {
    	ss.drivetrain.setState(DriveTrain.states.DISABLED);
    	ss.sucker.setState(Intake.states.DISABLED);
    	//ss.compressor.setState(Pneumatics.states.DISABLED);
    }
    
}
