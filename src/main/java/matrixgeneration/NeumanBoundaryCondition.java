package matrixgeneration;

public abstract class NeumanBoundaryCondition implements DoubleArgFunction{

	private double xl; 
	private double xr; 
	private double yl; 
	private double yr; 
	private static final double epsilon = 1e-5;
	public NeumanBoundaryCondition(double[] botLeftCoord, double size){
		xl = botLeftCoord[0]; 
		yl = botLeftCoord[1]; 
		xr = xl + size; 
		yr = yl + size;
		
	}
	
	private boolean valueOnLeftEdge(double x, double y){
		
		return Math.abs(x - xl) < epsilon; 
	}
	
	private boolean valueOnRightEdge(double x, double y){
		
		return Math.abs(x - xr) < epsilon; 
	}
	
	private boolean valueOnBotEdge(double x, double y){
		
		return Math.abs(y - yl) < epsilon; 
	}
	
	private boolean valueOnTopEdge(double x, double y){
		
		return Math.abs(y - yr) < epsilon; 
	}
	
	public abstract double computeValueOnLeftEdge(double x, double y);
	public abstract double computeValueOnRightEdge(double x, double y);
	public abstract double computeValueOnBotEdge(double x, double y);
	public abstract double computeValueOnTopEdge(double x, double y);
	
	
	public double computeValue(double x, double y, Direction direction){
		
		if(valueOnLeftEdge(x, y)){
			return computeValueOnLeftEdge(x, y);
		}
		if(valueOnRightEdge(x, y)){
			return computeValueOnRightEdge(x, y);
		}
		if(valueOnBotEdge(x, y)){
			return computeValueOnBotEdge(x, y);
		}
			
		if(valueOnTopEdge(x, y)){
			return computeValueOnTopEdge(x, y);
		}
		return 0;
		
	}

}
