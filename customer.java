package two_state_Simulation;

public class customer {
	private double arrivalTime;
	private int arrivalstate;
	
	public customer(double a, int b){
		arrivalTime = a;
		arrivalstate = b;
	}
	
	public double getArrTime(){
		return arrivalTime;
	}
	
	public int getArrState(){
		return arrivalstate;
	}

}
