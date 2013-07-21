package matrixgeneration;


public class GaussianQuadrature {

	private static final double[] roots = new double[] { -0.86113, -0.33998, 0.33998, 0.86113 };
	private static final double[] weights = new double[] { 0.34785, 0.65214, 0.65214, 0.34785 }; 
	
	//private static final double[] roots = new double[] {-0.5773502691896257, 0.5773502691896257 };
	//private static final double[] weights = new double[] { 1.0, 1.0 };
	
	private static double revertNormalization(double x, double lower, double upper){
		
		return (lower+upper)/2.0 + (upper - lower)/2.0 * x; 
		
	}
	
	
	public static double definiteDoubleIntegral(double lower1, double upper1, double lower2, double upper2, DoubleArgFunction functionToIntegrate){

		
		double integral = 0; 
		for(int i = 0 ; i<roots.length ; i++){
			for(int j = 0; j<roots.length ; j++){
				
				double v = weights[i]*weights[j]*functionToIntegrate.computeValue(revertNormalization(roots[i], lower1, upper1), revertNormalization(roots[j], lower2, upper2));
				integral += v; 
			}
		}
		
		
		
		return integral*(upper1 - lower1)*(upper2 -lower2)/4.0; 
		
	}
	
	
}

