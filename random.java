
import java.util.Date;

public class random {
	
	public static long mod = 1;
	public static long x;
	
	public random(){
		Date date = new Date();
		x = date.getTime();
		for(int i=0;i<35;i++) mod *=2;
		mod = mod - 31;
	}

	public static double UniformDistribution() {
		return (double)f(x)/mod;
	}
	
	public static long f(long a){
		return x = (5*5*5*5*5*a) % mod;
	}
	
	public double exprand(double para){
		double s = UniformDistribution();
		double y = Math.log(1/s)/para;
		
		return y;
	}

}
