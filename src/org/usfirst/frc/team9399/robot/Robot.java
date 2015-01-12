package org.usfirst.frc.team9399.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team9399.systems.*;

public class Robot extends IterativeRobot {
	
	Controls control;
	DriveTrain drivetrain;
	int driveState = DriveTrain.states.FIELD_CENTRIC;
	
	public void robotInit() {
		control = new Controls(Config.Controls.JOY_LEFT,Config.Controls.JOY_RIGHT);
		drivetrain = new DriveTrain(Config.DriveTrain.FRONT_LEFT,Config.DriveTrain.FRONT_RIGHT,
				Config.DriveTrain.REAR_LEFT,Config.DriveTrain.REAR_RIGHT,Config.DriveTrain.GYRO_PORT
				,Config.DriveTrain.GYRO_SENSE);
		drivetrain.setState(DriveTrain.states.DISABLED);
    }

	
	public void autonomousInit()
	{
		drivetrain.setState(DriveTrain.states.DISABLED); // Reset the gyro
		drivetrain.setState(driveState);
		
	}
    
    public void autonomousPeriodic(){

    }

    public void teleopInit(){
    	drivetrain.setState(driveState);
    	
    }
   
    public void teleopPeriodic() {
    	double[] heading=control.getHeading();
    	drivetrain.setHeading(heading);
    	drivetrain.run();
    	if(control.isResetPressed() ){
    		drivetrain.state = DriveTrain.states.RESET_FIELD_REF;
    	}else if(drivetrain.state != driveState){
    		drivetrain.state = driveState;
    	}
    }
    
    
    public void testPeriodic() {
    
    }
    
}
