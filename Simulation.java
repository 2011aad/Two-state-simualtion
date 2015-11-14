
public class Simulation {
	public static void main(String[] args){
		int numofpara = 9;
		double data[][] = new double[2][numofpara];
		int numofsim = 1000000;
		stochasticProcess testProcess;
		for(int i=1;i<=numofpara;i++){
			testProcess = new stochasticProcess(numofsim, (double)i*10, 10, 10, 1);   //lambdac, lambdas, muc, mus
			data[0][i-1] = i*10;
			data[1][i-1] = testProcess.getResult();
		}
		new exportToExcel(data, "lambda", "mean queue length");
	}
}
