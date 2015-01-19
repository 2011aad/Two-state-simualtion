package two_state_Simulation;

import java.util.*;

public class stochasticProcess {
	static random R = new random();
	static enum states{arr,dep,tran};
	static Queue<customer> buffer = new LinkedList<customer>();
	static Queue<customer> unsolved = new LinkedList<customer>();
	
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
	static double last_arrtime,last_deptime,last_trantime; //last_arrtime means the arrival time of the packet when queue is empty
	
	public double [] residual_service_time = new double [2];
	public double [] service_time = new double [2];
	public double [][] residual_service_time_state = new double [2][40];
	public double [][] service_time_state = new double [2][40];
	public int [] arrivals = new int [2];
	public int [] departures = new int [2];
	public double WT;
	public double[][] data = {residual_service_time_state[0],residual_service_time_state[1],service_time_state[0],service_time_state[1]};							//data for other class using 
	public int [][] counters = new int[2][2];
	public double [] idle_time = new double [2];
	public double [] busy_time = new double [2];
	public double [] P0 = new double [2];
	
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
				if(buffer.size()==0) idle_time[sysStat] += (arri - systime);
				else busy_time[sysStat] += (arri - systime);
				systime = arri;
				c = new customer(systime, sysStat);
				if((sysStat==1)){						//state 0

				}
				else{								//state 1
					
				}
				
				if(buffer.size()==0){				//empty
					last_arrtime = systime;
					c.setStartServiceTime(systime);
					c.setStartServiceState(sysStat);
					arrivals[c.getArrState()]++;
				}
				
				else{								//nonempty
					unsolved.add(c);
				}
				
				if(buffer.size()<10000)
					buffer.add(c);
				last_action = states.arr;
			}
			
			if(departure<arri && departure<trans){							//departure happens first
				busy_time[sysStat] += (departure - systime);
				systime = departure;
				last_action = states.dep;
				
				c = buffer.remove();
				WT = systime - c.getStartServiceTime();
				if(WT<2){
					service_time_state[c.getStartServiceState()][(int)(WT*20)]++;
					counters[0][c.getStartServiceState()]++;
				}
				service_time[c.getStartServiceState()] += WT;
				departures[c.getStartServiceState()]++;
				
				while(!unsolved.isEmpty()){
					c = unsolved.remove();
					WT = systime - c.getArrTime();
					if(WT<2){
						residual_service_time_state[c.getArrState()][(int)(WT*20)]++;
						counters[1][c.getArrState()]++;
					}
					residual_service_time[c.getArrState()] += WT;
					arrivals[c.getArrState()]++;
				}
				
				if(!buffer.isEmpty()){
					buffer.element().setStartServiceState(sysStat);
					buffer.element().setStartServiceTime(systime);
				}

				if(sysStat==0){					//state 0
					
				}
				
				else{							//state1

				}
				totalWT += (systime - c.getArrTime());
				counter++;
				last_deptime = systime;
			}
			
			if(trans<departure && trans<arri){									//state transition happens first
				if(buffer.size()==0) idle_time[sysStat] += (trans - systime);
				else busy_time[sysStat] += (trans - systime);
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
		for(int i=0;i<service_time_state.length;i++)
			for(int j=0;j<service_time_state[i].length;j++)
				service_time_state[i][j] /= counters[0][i];
		for(int i=0;i<residual_service_time_state.length;i++)
			for(int j=0;j<residual_service_time_state[i].length;j++)
				residual_service_time_state[i][j] /= counters[1][i];
		
		for(int i=0;i<P0.length;i++)
			P0[i] = (idle_time[i]/(idle_time[i]+busy_time[i]));
		
		print_out(numofcus);
	}
	
//	private double find_max(double data1,double data2,double data3){
//		if(data1>data2 && data1>data3) return data1;
//		if(data2>data1 && data2>data3) return data2;
//		return data3;
//	}
	
	private void print_out(int numofcus){
		System.out.println("average waiting time: " + totalWT/numofcus);
		System.out.println("residual service time of state 0 arrivals: " + residual_service_time[0]/arrivals[0]);
		System.out.println("residual service time of state 1 arrivals: " + residual_service_time[1]/arrivals[1]);
		System.out.println("service time of start at state 0: " + (1-P0[0])*service_time[0]/departures[0]);
		System.out.println("service time of start at state 1: " + (1-P0[1])*service_time[1]/departures[1]);
	}
}
