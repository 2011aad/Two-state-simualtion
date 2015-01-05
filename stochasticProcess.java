package two_state_Simulation;

import java.util.*;

public class stochasticProcess {
	static random R = new random();
	static enum states{arr,dep,tran};
	static Queue<customer> buffer = new LinkedList<customer>();
	static final double lambda = 1;
	static final double mu0 = 2;
	static final double mu1 = 2;
	static final double f0 = 1;
	static final double f1 = 1;
	static double arri, departure, trans;
	static boolean sysStat = false;		
	static double systime = 0;
	static states last_action = states.arr;
	static double totalWT = 0;
	static double last_arrtime,last_deptime,last_trantime; //last_arrtime means the arrival time of the packet when queue is empty
	public double[][] data = {};							//data for other class using 
	
	public stochasticProcess(int numofcus){
		for(int i=0;i<1;i++)
			buffer.add(new customer(systime));
		int counter = 0;
		customer c;

		arri = R.exprand(lambda);							//the occurrence time of next arrival
		departure = R.exprand(mu0);							//the occurrence time of next departure
		trans = R.exprand(f0);								//the occurrence time of next state transition
		
		while(counter<numofcus){
			switch(last_action){							//determine which action last loop occurs
				case arr:arri = systime + R.exprand(lambda);break;
				case dep:departure = (buffer.isEmpty()?arri:systime) + (sysStat?R.exprand(mu1):R.exprand(mu0));
					   	break;
				case tran:trans = systime + ((sysStat)?R.exprand(f1):R.exprand(f0));
						departure = (buffer.isEmpty()?arri:systime) + (sysStat?R.exprand(mu1):R.exprand(mu0));
						break;
			}										//if buffer is empty, then random a departure time after next arrival						
	
			//compare which action occurs first
			if(arri<departure && arri<trans){								//arrival happens first	
				systime = arri;
				if(sysStat){						//state 0

				}
				else{								//state 1

				}
				if(buffer.size()==0)
					last_arrtime = systime;
				
				if(buffer.size()<10000)
					buffer.add(new customer(systime));
				last_action = states.arr;
			}
			
			if(departure<arri && departure<trans){							//departure happens first
				systime = departure;
				last_action = states.dep;
				
				c = buffer.remove();

				if(!sysStat){					//state 0

				}
				
				else{							//state1

				}
				totalWT += (systime - c.getArrTime());
				counter++;
				last_deptime = systime;
			}
			
			if(trans<departure && trans<arri){									//state transition happens first
				systime = trans;
				if(!sysStat && buffer.isEmpty()){				//state 0 & empty

				}
				if(sysStat && buffer.isEmpty()){				//state 0 & empty

				}
				if(!sysStat && !buffer.isEmpty()){				//state 0 & nonempty

				}
				if(sysStat && !buffer.isEmpty()){				//state 1 & nonempty

				}
				
				sysStat = !sysStat;
				last_action = states.tran;
				last_trantime = systime;
				
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
	}
}
