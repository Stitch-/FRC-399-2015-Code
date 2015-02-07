package org.usfirst.frc.team9399.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class PIDLoop {
	double p,i,d;
	double prop,inte,der=0;
	double targ,curr,errMem;
	double out=0;
	long deltaTime,timeMem=0;
	int mult=1;
	
	
	public PIDLoop(double p,double i,double d){
		this.setPID(p,i,d);
	}
	
	public void setPID(double p,double i,double d){
		this.p=p;
		this.i=i;
		this.d=d;
		SmartDashboard.putNumber("P", p);
		SmartDashboard.putNumber("I", i);
		SmartDashboard.putNumber("D", d);
	}
	
	
	void tick(double err){

            if (Math.abs(err)
                    > 180) {
                if (err > 0) {
                    err = err - 360;
                } else {
                   err = err + 360;
                }
            }
		
        /*    
        p = SmartDashboard.getNumber("P");    
        i = SmartDashboard.getNumber("I"); 
        d = SmartDashboard.getNumber("D"); 
        */
		
		long time = System.currentTimeMillis();
		if(timeMem!=0){
			deltaTime = time-timeMem;
		}else{
			deltaTime = 1;
		}
		prop=p*err;
		inte=i*(inte+(err*deltaTime));
		der=d*((err - errMem)/deltaTime);
		out=prop+inte+der; //+curr;
		if(out > 1) out = 1;
		else if(out < -1) out = -1;
		SmartDashboard.putNumber("OUT", out);
		timeMem=time;
	}
	
	public double correct(double targ, double curr){
		/*if(curr>targ){
			mult=-1;
		}else{
			mult=1;
		}*/
		this.targ=targ;
		this.curr=curr;
		tick(targ-curr);
		
		SmartDashboard.putNumber("TARG", targ);
		SmartDashboard.putNumber("CURR", curr);
		return out;
	}
	
}
