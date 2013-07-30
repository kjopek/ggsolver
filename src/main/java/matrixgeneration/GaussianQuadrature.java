package matrixgeneration;


public class GaussianQuadrature {

	private static final double[] roots = new double[] { -0.86113, -0.33998, 0.33998, 0.86113 };
	private static final double[] weights = new double[] { 0.34785, 0.65214, 0.65214, 0.34785 }; 
	
	//private static final double[] roots = new double[] {-0.5773502691896257, 0.5773502691896257 };
	//private static final double[] weights = new double[] { 1.0, 1.0 };
	
	private static double revertNormalization(double x, double lower, double upper){
		
		return (lower+upper)/2.0 + (upper - lower)/2.0 * x; 
		
	}
	
	
	public static double definiteDoubleIntegral(double lower1, double upper1, double lower2, double upper2, DoubleArgFunction functionToIntegrate, Direction direction){	

		double integral = 0; 
		for(int i = 0 ; i<roots.length ; i++){
			for(int j = 0; j<roots.length ; j++){
				
				double v = weights[i]*weights[j]*functionToIntegrate.computeValue(revertNormalization(roots[i], lower1, upper1), revertNormalization(roots[j], lower2, upper2), direction);
				integral += v; 
			}
		}	
		
		return integral*(upper1 - lower1)*(upper2 -lower2)/4.0; 
		
	}
	
	public static double definiteIntegralOverBoundaries(double xl, double xr, double yl,
			double yr, DoubleArgFunction functionToIntegrate, Direction direction) {

		double integral = 0;
		//left element boundary
		for(int i = 0; i < roots.length; i++){
			integral+= weights[i]*functionToIntegrate.computeValue(xl, revertNormalization(roots[i], yl, yr), direction)
				*(yr - yl)/2.0;
		}
		
		//right element boundary
		for(int i = 0; i < roots.length; i++){
			integral+= weights[i]*functionToIntegrate.computeValue(xr, revertNormalization(roots[i], yl, yr), direction)
					*(yr-yl)/2.0;
		}
//		if(yl == 0.0 || yr == 2.0)
//			System.out.println("START");
		//bot element boundary
		for(int i = 0; i < roots.length; i++){
			integral+= weights[i]*functionToIntegrate.computeValue(revertNormalization(roots[i], xl, xr), yl, direction)
					*(xr-xl)/2.0; 
		}
		
		//top element boundary
		for(int i = 0; i < roots.length; i++){
			integral+= weights[i]*functionToIntegrate.computeValue(revertNormalization(roots[i], xl, xr), yr, direction)
					*(xr-xl)/2.0;
		}
//		if(yl == 0.0 || yr == 2.0)
//			System.out.println("END");
		return integral;

	}
	
	
}

