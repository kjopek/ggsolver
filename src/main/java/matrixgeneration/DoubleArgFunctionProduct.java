package matrixgeneration;

public class DoubleArgFunctionProduct implements DoubleArgFunction {

	private DoubleArgFunction function1; 
	private DoubleArgFunction function2; 

	
	public void setFunctions(DoubleArgFunction function1, DoubleArgFunction function2){
		this.function1 = function1;
		this.function2 = function2;	
	}
	
	@Override
	public double computeValue(double x, double y, Direction direction) {
		return function1.computeValue(x, y, direction)*function2.computeValue(x, y, direction);
	}

}
