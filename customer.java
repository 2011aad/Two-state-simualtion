

public class customer {
	private double arrivalTime;
	private int arrivalstate;
	private double startServiceTime;
	private int startServiceState;
	
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
	
	public double getStartServiceTime(){
		return startServiceTime;
	}
	
	public void setStartServiceTime(double t){
		startServiceTime = t;
	}
	
	public int getStartServiceState(){
		return startServiceState;
	}
	
	public void setStartServiceState(int t){
		startServiceState = t;
	}

}
