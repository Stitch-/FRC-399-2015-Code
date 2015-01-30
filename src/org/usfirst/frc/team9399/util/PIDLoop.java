package org.usfirst.frc.team9399.util;

public class PIDLoop {
	double p,i,d;
	double prop,inte,der=0;
	double targ,curr,errMem;
	double out=0;
	long deltaTime,timeMem=0;
	
	public PIDLoop(double p,double i,double d){
		this.setPID(p,i,d);
	}
	
	public void setPID(double p,double i,double d){
		this.p=p;
		this.i=i;
		this.d=d;
	}
	
	void tick(double err){
		long time = System.currentTimeMillis();
		deltaTime = time-timeMem;
		prop=p*err;
		inte=i*(inte+(err*deltaTime));
		der=d*((err - errMem)/deltaTime);
		out=curr+prop+inte+der;
		timeMem=time;
	}
	
	public double correct(double targ, double curr){
		this.targ=targ;
		this.curr=curr;
		tick(targ-curr);
		return out;
	}
	
}
