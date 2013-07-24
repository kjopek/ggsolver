package pl.edu.agh.mes.gg;

import pl.edu.agh.mes.gg.twod.TreeBuilder;
import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;

public class TreeBuilderTest {
	public static void main(String args[]) {
		TreeBuilder treeBuilder = new TreeBuilder();
		DoubleArgFunction f = new DoubleArgFunction() {
			@Override
			public double computeValue(double x, double y) {
				return 1; 
			}
		};
		treeBuilder.buildTree(128, -1, 1, 2, f);
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		double [][] matrix;
		double [] rhs;
		matrixGenerator.createMatrixAndRhs(128, 0, 0, 2,f);
		
		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();
		
		
		MatrixUtils.eliminate(rhs.length, matrix, rhs);
		MatrixUtils.backwardSubstitution(matrix, rhs, matrix.length-1);

		
		
	}
}
