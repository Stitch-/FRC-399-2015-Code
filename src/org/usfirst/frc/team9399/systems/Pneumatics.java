package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.Compressor;
import org.usfirst.frc.team9399.util.SubSystem;

public class Pneumatics extends SubSystem {
	
	Compressor compressor;
	int state = states.DISABLED;
	int stateMem=states.DISABLED;
	
	public Pneumatics(int comp){
		compressor = new Compressor(comp);
	}
	
	public boolean isCompressing(){
		return compressor.getPressureSwitchValue();
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
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
