package com.project.remoteclient.process;

public class MouseClientProcess {
	private float x ;
	private float y ;
	private float x_last ;
	private float y_last ;
	
	private float x_difference;
	private float y_difference;
	
	public int getX_difference() {
		return (int) x_difference*2;
	}
	public int getY_difference() {
		return (int) y_difference*2;
	}
	
	//update the previous co-ordinate and generate new co-ordinate 
	public void updateCoordinate(float x, float y){
		x_last = this.x;
	    y_last = this.y;
	      
	    this.x =x;//motionEvent.getX();
	    this.y=y;//motionEvent.getY();

	    generateDifference();
	}
	public void generateDifference()
	{
		x_difference=x-x_last ;
   	 	y_difference=y-y_last ;   	 
	}
	
	public boolean shouldDataBeSend(){
		if (x_difference==0.0 && y_difference==0.0)
			return false;		
		else
			return true;
		
	}

}
