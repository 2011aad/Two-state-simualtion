package two_state_Simulation;

public class Simulation {
	public static void main(String[] args){
		stochasticProcess testProcess = new stochasticProcess(1000000);
		new exportToExcel(testProcess.data);
	}
}
