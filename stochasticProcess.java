
import java.util.*;

public class stochasticProcess {
	static random R = new random();
	static enum states{arr,dep};
	static Queue<customer> buffer = new LinkedList<customer>();
	
	static final double lambda = 2;
	static final double mu0 = 3;
	static final double mu1 = 8;
	static final double f0 = 0.002;
	static final double f1 = 0.003;
	static double arri, departure;
	static int sysStat = 0;		
	static double systime = 0;
	static states last_action = states.arr;
	static double totalWT = 0;
	public double [][] residual_service_time_state = new double [2][40];
	public double [][] service_time_state = new double [2][40];
	public double[][] data = {residual_service_time_state[0],residual_service_time_state[1],service_time_state[0],service_time_state[1]};							//data for other class using 

	
	public stochasticProcess(int numofcus){
		for(int i=0;i<1;i++)
			buffer.add(new customer(systime,0));
		int counter = 0;
		customer c;

		arri = R.exprand(lambda);							//the occurrence time of next arrival
		departure = R.exprand(mu0);							//the occurrence time of next departure
		
		while(counter<numofcus){
			switch(last_action){							//determine which action last loop occurs
				case arr:
					arri = systime + R.exprand(lambda);break;
				case dep:
					if(sysStat==0) sysStat = (R.UniformDistribution()<f0/mu0)?1:0;
					else sysStat = (R.UniformDistribution()<f1/mu1)?0:1;
					departure = (buffer.isEmpty()?arri:systime) + ((sysStat==1)?R.exprand(mu1):R.exprand(mu0));
					break;
			}										//if buffer is empty, then random a departure time after next arrival						
	
			//compare which action occurs first
			if(arri<departure){								//arrival happens first
				systime = arri;
				c = new customer(systime, sysStat);
				if((sysStat==1)){						//state 0

				}
				else{								//state 1
					
				}
				
				if(buffer.size()<10000)
					buffer.add(c);
				last_action = states.arr;
			}
			
			else{							//departure happens first
				systime = departure;
				last_action = states.dep;
				
				c = buffer.remove();

				if(sysStat==0){					//state 0
					
				}
				
				else{							//state1

				}
				totalWT += (systime - c.getArrTime());
				counter++;
			}
		}
		
		print_out(numofcus);
	}
	
//	private double find_max(double data1,double data2,double data3){
//		if(data1>data2 && data1>data3) return data1;
//		if(data2>data1 && data2>data3) return data2;
//		return data3;
//	}
	
	private void print_out(int numofcus){
		System.out.println("average waiting time: " + totalWT/numofcus);
//		System.out.println("Queue length at state1 to state0: " + (double)L1/(test_point/2));
//		System.out.println("Queue length at state0 to state1: " + (double)L0/(test_point/2));
//		System.out.print("effective mu1: " + ((f0+f1)*lambda-mu0*f1)/f0);
//		System.out.println("residual service time of state 0 arrivals: " + residual_service_time[0]/arrivals[0]);
//		System.out.println("residual service time of state 1 arrivals: " + residual_service_time[1]/arrivals[1]);
//		System.out.println("service time of start at state 0: " + (1-P0[0])*service_time[0]/departures[0]);
//		System.out.println("service time of start at state 1: " + (1-P0[1])*service_time[1]/departures[1]);
	}
}
