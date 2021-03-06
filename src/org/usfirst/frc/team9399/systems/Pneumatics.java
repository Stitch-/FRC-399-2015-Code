package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.Compressor;

import org.usfirst.frc.team9399.util.SubSystem;

public class Pneumatics extends SubSystem {
	
	Compressor compressor;
	int stateMem=states.DISABLED; //mem val to prevent us from setting closed loop control twice
	
	/**
	 * @author Aaron Elersich (aelersich1@gmail.com)
	 * @param comp
	 */
	public Pneumatics(int comp){
		compressor = new Compressor(comp);
	}
	
	/**An index for the different states*/
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	/**Is the compressor active?*/
	public boolean isCompressing(){
		return compressor.getPressureSwitchValue();
	}
	
	public void run(){
		switch(this.state){
			case states.DISABLED:
				if(stateMem!=state){
					compressor.setClosedLoopControl(false);
					stateMem=state;
				}
			break;
			case states.ENABLED:
				if(stateMem!=state){
					compressor.setClosedLoopControl(true);
					stateMem=state;
				}
			break;
		}
		
	}
	
}
