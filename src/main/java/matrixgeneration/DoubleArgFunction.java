package matrixgeneration;

public interface DoubleArgFunction {
	
		double computeValue(double x, double y);
		double computeDerivativeValue(double x, double y, Direction direction);
}
