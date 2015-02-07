package org.usfirst.frc.team399.systems;

import edu.wpi.first.wpilibj.SerialPort;
import com.kauailabs.nav6.frc.*;


public class IMU {
	
	private SerialPort sp;
	public IMUAdvanced IMUA;
	
	private byte updateRate=100;
	
	private static IMU instance = null;
	private IMU()
	{
		try{
			sp=new SerialPort(57600,SerialPort.Port.kMXP);
			IMUA=new IMUAdvanced(sp,updateRate);
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.out.println(e.getMessage());
		}
		
	}

	
	
	public static IMU getInstance()
	{
		if(instance == null)
		{
			instance = new IMU();
		}
		return instance;
	}
}
