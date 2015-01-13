package two_state_Simulation;

import java.util.*;

public class stochasticProcess {
	static random R = new random();
	static enum states{arr,dep,tran};
	static Queue<customer> buffer = new LinkedList<customer>();
	
	static final double lambda = 1;
	static final double mu0 = 2;
	static final double mu1 = 3;
	static final double f0 = 4;
	static final double f1 = 1;
	static double arri, departure, trans;
	static int sysStat = 0;		
	static double systime = 0;
	static states last_action = states.arr;
	static double totalWT = 0;
	static double busy_time = 0;
	static double last_arrtime,last_deptime,last_trantime; //last_arrtime means the arrival time of the packet when queue is empty
	
	public double WT;
	public double[][] data = {};							//data for other class using 
	
	public stochasticProcess(int numofcus){
		for(int i=0;i<1;i++)
			buffer.add(new customer(systime,0));
		int counter = 0;
		customer c;

		arri = R.exprand(lambda);							//the occurrence time of next arrival
		departure = R.exprand(mu0);							//the occurrence time of next departure
		trans = R.exprand(f0);								//the occurrence time of next state transition
		
		while(counter<numofcus){
			switch(last_action){							//determine which action last loop occurs
				case arr:arri = systime + R.exprand(lambda);break;
				case dep:departure = (buffer.isEmpty()?arri:systime) + ((sysStat==1)?R.exprand(mu1):R.exprand(mu0));
					   	break;
				case tran:trans = systime + ((sysStat==1)?R.exprand(f1):R.exprand(f0));
						departure = (buffer.isEmpty()?arri:systime) + ((sysStat==1)?R.exprand(mu1):R.exprand(mu0));
						break;
			}										//if buffer is empty, then random a departure time after next arrival						
	
			//compare which action occurs first
			if(arri<departure && arri<trans){								//arrival happens first	
				if(buffer.size()>0){
					busy_time += arri - systime;
				}
				systime = arri;
				c = new customer(systime, sysStat);
				if((sysStat==1)){						//state 0

				}
				else{								//state 1
					
				}
				
				if(buffer.size()==0){				//empty
					last_arrtime = systime;
				}
				
				else{								//nonempty

				}
				
				if(buffer.size()<10000)
					buffer.add(c);
				last_action = states.arr;
			}
			
			if(departure<arri && departure<trans){							//departure happens first
				busy_time += departure - systime;
				systime = departure;
				last_action = states.dep;
				
				c = buffer.remove();
				
				if(!buffer.isEmpty()){

				}

				if(sysStat==0){					//state 0
					
				}
				
				else{							//state1

				}
	
				counter++;
				last_deptime = systime;
			}
			
			if(trans<departure && trans<arri){									//state transition happens first
				if(buffer.size()>0){
					busy_time += trans - systime;
				}
				systime = trans;
				if(sysStat==0 && buffer.isEmpty()){				//state 0 & empty

				}
				if(sysStat==1 && buffer.isEmpty()){				//state 0 & empty

				}
				if(sysStat==0 && !buffer.isEmpty()){				//state 0 & nonempty

				}
				if(sysStat==1 && !buffer.isEmpty()){				//state 1 & nonempty

				}
				
				sysStat = 1-sysStat;
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
		System.out.println(busy_time/systime);
		System.out.println((f0+f1+(f1*mu1+f0*mu0)/(f0+f1)+lambda)/(mu0+mu1+f0+f1));
	}
}
