package org.usfirst.frc.team9399.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import org.usfirst.frc.team9399.programs.*;
import org.usfirst.frc.team9399.systems.*;
//import org.usfirst.frc.team9399.util.FileLogger;
import org.usfirst.frc.team9399.util.Toggler;


public class Main extends IterativeRobot {
	
	SuperSystem ss; //class that contains all subsystems. Mainly for instantiation and managements of subsystems.
	static final int mode = DriveTrain.states.FIELD_CENTRIC;
	int driveState = mode; //choose whether to drive with the gyroscope and/or pid, mainly for troubleshooting.
	boolean[] padButtons;
	boolean tank;
	Toggler tankDrive=new Toggler();
	//Toggler turbo=new Toggler();
	Toggler roboCent=new Toggler();
	SendableChooser autonChooser=new SendableChooser();
	Scheduler sch;
	PowerDistributionPanel pdp;
	String modeInd,wingIndLeft,wingIndRight="";
	//FileLogger captainsLog = new FileLogger("/home/lvuser/logs/"); //Intstantiate's Justin's file logger. 
																//all console outputs will be saved to a log.
	
	public void robotInit() {
		ss = SuperSystem.getInstance();
		autonChooser.addObject("Grab SRC and drive back straight"
				,new GrabSRCDriveBackStraightDebugFaster());
		autonChooser.addObject("San Diego SRC Grab"
				,new GrabSRCDriveBackStraightDebug());
		autonChooser.addObject("Do nothing", new DoNothing());
		autonChooser.addObject("Test",new Test());
		autonChooser.addObject("Grab SRC from step and turn",new GrabSRCAuton());
		autonChooser.addObject("Grab left SRC",new GrabSRCLeft());
		autonChooser.addObject("Grab right SRC",new GrabSRCRight());
		autonChooser.addObject("Stack yellow totes", new StackYellowTotes3());
		//autonChooser.addObject("Grab RCs",new GrabRCS());
		SmartDashboard.putData("Choose Auton",autonChooser);
		sch=Scheduler.getInstance();
		pdp = new PowerDistributionPanel();
		pdp.clearStickyFaults();
    }
	
	public void testInit() {
		//ss.drivetrain.initEncoders();
		ss.drivetrain.setState(DriveTrain.states.PRINT_FROM_ENCODERS);
	}
	
	public void testPeriodic() {
		ss.drivetrain.run();
	}

	
	public void autonomousInit() {
		CommandGroup curr=(CommandGroup) autonChooser.getSelected();
		sch.add(curr);
		ss.drivetrain.run();
		ss.drivetrain.setState(DriveTrain.states.ROBOT_CENTRIC);
		ss.wingeyBits.setState(Wings.states.ENABLED);
		ss.funkyClips.setState(Lifter.states.ENABLED);
		ss.compressor.setState(Pneumatics.states.ENABLED);
		ss.sucker.setState(Intake.states.ENABLED);
	}

    public void autonomousPeriodic(){
    	sch.run();
    	ss.drivetrain.run();
    	ss.wingeyBits.run();
    	ss.funkyClips.run();
    	ss.compressor.run();
    	ss.sucker.run();
    }
    
    public void practiceInit(){
    	ss.compressor.setState(Pneumatics.states.ENABLED);
    }
    
    public void practicePeriodic(){
    	ss.compressor.run();
    }

    public void teleopInit() {
    	ss.drivetrain.setState(driveState);
    	ss.wingeyBits.setState(Wings.states.ENABLED);
    	ss.sucker.setState(Intake.states.ENABLED);
    	ss.funkyClips.setState(Lifter.states.ENABLED);
    	ss.compressor.setState(Pneumatics.states.ENABLED);
    }
   
    public void teleopPeriodic(){
    	boolean toggleTank =  ss.control.getButton(Controls.pads.LEFT, 5);
    	//boolean toggleTurbo = ss.control.getButton(Controls.pads.LEFT, 12);
    	boolean toggleCentric = ss.control.getButton(Controls.pads.LEFT, 10);
    	tankDrive.set(toggleTank);
    	//turbo.set(toggleTurbo);
    	roboCent.set(toggleCentric);
    	
    	//System.out.println(ss.funkyClips.getSwitch(true));
   
    	double[] heading;
   
    	if(tankDrive.get()){
    		driveState=DriveTrain.states.TANK_DRIVE;
    		heading = ss.control.getHeadingTank();
    		modeInd="Tank";
    	}else{
    		if(roboCent.get()){
    			driveState=DriveTrain.states.ROBOT_CENTRIC;
    			modeInd="Robot Centric";
    		}else{
        		driveState=mode;  
        		modeInd="Field Centric";

    		}
    		heading=ss.control.getHeading();
    	}
    	wingIndLeft=ss.wingeyBits.getHook(Wings.wings.LEFT)?"Disengaged":"Engaged";
    	wingIndRight=ss.wingeyBits.getHook(Wings.wings.RIGHT)?"Disengaged":"Engaged";
    	
    	SmartDashboard.putString("Left Talon",wingIndLeft);
    	SmartDashboard.putString("Right Talon",wingIndRight);
    	SmartDashboard.putString("Mode",modeInd);
    	SmartDashboard.putNumber("Angle",ss.drivetrain.getYaw());
    	//ss.drivetrain.setTurbo(!turbo.get());
    	
    	//System.out.println(heading[0]+ " "+heading[1]);
    	padButtons=ss.toggleControls();
    	boolean[] wingCommand={
    			ss.control.getButton(Controls.pads.OP, 6),
    			ss.control.getButton(Controls.pads.OP, 8),
    			ss.control.getButton(Controls.pads.OP, 7),
    			ss.control.getButton(Controls.pads.OP, 5)
    	};
    	ss.wingeyBits.setCommand(wingCommand);
    	
    	//ss.wingeyBits.setState(Wings.wings.LEFT,padButtons[0]?0:1);
    	//ss.wingeyBits.setState(Wings.wings.RIGHT,padButtons[1]?0:1);
    	
    	ss.sucker.actuateIntake(padButtons[1]);
    	ss.funkyClips.actuateClaw(padButtons[2]);
    	ss.funkyClips.actuateClips(padButtons[0]);
    	ss.wingeyBits.actuateHook(Wings.wings.LEFT,padButtons[3]);
    	ss.wingeyBits.actuateHook(Wings.wings.RIGHT,padButtons[4]); 
    	ss.drivetrain.setHeading(heading);
    	ss.sucker.setWheels(ss.control.getOpPadPOV());
    	ss.funkyClips.setSpeed(-ss.control.getOpPadRight()[Config.KeyMap.LIFTER_AXIS]);
    	ss.drivetrain.run();
    	ss.sucker.run();
    	ss.compressor.run();
    	ss.wingeyBits.run();
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
    	ss.funkyClips.setState(Lifter.states.DISABLED);
    	ss.wingeyBits.setState(Wings.states.DISABLED);
    	ss.disable();
    	ss.compressor.setState(Pneumatics.states.DISABLED);
    }
    
    public void disabledPeriodic(){
    	ss.drivetrain.run();
    	ss.wingeyBits.run();
    }
}
