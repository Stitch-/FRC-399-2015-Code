package org.usfirst.frc.team9399.util;

public interface SubSystem {
	
	int state=0;
	
	public abstract void run();
	
	public class states{
		public static final int DISABLED = 0;
	}
	
	
	
}