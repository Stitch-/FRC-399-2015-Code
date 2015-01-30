package org.usfirst.frc.team9399.robot;

public class Config {

	public class DriveTrain{
		public static final int FRONT_RIGHT=1;
		public static final int FRONT_LEFT=0;
		public static final int REAR_RIGHT=4;
		public static final int REAR_LEFT=2;
		public static final int GYRO_PORT = 0;
		public static final double GYRO_SENSE = -0.007;
		public static final double P_TUNING = 0;
		public static final double I_TUNING = 0;
		public static final double D_TUNING = 0;
	}
	
	public class Controls{
		public static final int JOY_LEFT=0;
		public static final int JOY_RIGHT=1;
		public static final double DEADBAND=0.1;
	}
	
}
