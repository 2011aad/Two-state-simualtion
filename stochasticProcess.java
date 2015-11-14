import java.util.*;

public class stochasticProcess {
	private random R = new random();
	enum states{customer_arr,customer_dep,tran}
	private Queue<customer> customer_buffer = new LinkedList<customer>();
	private Queue<customer> server_buffer = new LinkedList<customer>();
	
	private double lambdas = 10;
	private double lambdac = 1;
	private double mus = 1;
	private double muc = 10;
	private double arri, departure, trans;
	private double systime = 0;
	private boolean server_arr_or_dep;
	private states last_action = states.customer_arr;
	private double totalWT = 0;
	private int customerQL = 0, serverQL = 0, tests = 0;
	private double last_arrtime,last_deptime,last_trantime; //last_arrtime means the arrival time of the packet when queue is empty
	private int numofcus;
//	public double[][] data = {residual_service_time_state[0],residual_service_time_state[1],service_time_state[0],service_time_state[1]};							//data for other class using

	public stochasticProcess(int ns, double lc, double ls, double mc, double ms){
		lambdas = ls;
		lambdac = lc;
		mus = ms;
		muc = mc;
		numofcus = ns;
		double a,b;

		for(int i=0;i<5;i++)
			server_buffer.add(new customer(systime, 0));
		int counter = 0;
		customer c;

		arri = R.exprand(lambdac);							//the occurrence time of next arrival
		departure = R.exprand(server_buffer.size()*muc);							//the occurrence time of next departure
		trans = R.exprand(lambdas);								//the occurrence time of next state transition
		
		while(counter<numofcus){
			switch(last_action){							//determine which action last loop occurs
				case customer_arr:arri = systime + R.exprand(lambdac);break;
				case customer_dep:departure = (customer_buffer.isEmpty()?arri:systime) + R.exprand(server_buffer.size()*muc);
					   	break;
				case tran:
					a = R.exprand(lambdas);
					b = R.exprand(server_buffer.size()*mus);
					server_arr_or_dep = a<b;
					trans = systime + (server_arr_or_dep?a:b);
					departure = (customer_buffer.isEmpty()?arri:systime) + R.exprand(server_buffer.size()*muc);
					break;
			}										//if buffer is empty, then random a departure time after next arrival						
	
			//compare which action occurs first
			if(arri<departure && arri<trans){								//arrival happens first
				systime = arri;
				c = new customer(systime, server_buffer.size());

				last_arrtime = systime;
				c.setStartServiceTime(systime);
				c.setStartServiceState(server_buffer.size());
				customerQL += customer_buffer.size();
				serverQL += server_buffer.size();
				tests++;

				if(customer_buffer.size()<10000)
					customer_buffer.add(c);
				last_action = states.customer_arr;
			}
			
			if(departure<arri && departure<trans){							//departure happens first
				systime = departure;
				last_action = states.customer_dep;
				
				if(!customer_buffer.isEmpty()){
					c = customer_buffer.remove();
					totalWT += (systime - c.getArrTime());
					counter++;
				}

				last_deptime = systime;
			}
			
			if(trans<departure && trans<arri){									//state transition happens first
				systime = trans;
				if(server_arr_or_dep) server_buffer.add(new customer(systime,server_buffer.size()));
				else {
					server_buffer.remove();
				}

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

	public double getResult(){return totalWT/numofcus*lambdac;}
	
	private void print_out(int numofcus){
		System.out.println("average waiting time: " + totalWT/numofcus);
//		System.out.println("customer mean queue length: " + (double)customerQL/tests);
//		System.out.println("server mean queue length: " + (double)serverQL/tests);
	}
}
