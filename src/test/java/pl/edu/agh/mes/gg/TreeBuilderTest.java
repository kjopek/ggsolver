package pl.edu.agh.mes.gg;

import java.util.List;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

public class TreeBuilderTest {
	public static void main(String args[]) {
		TreeBuilder treeBuilder = new TreeBuilder();
		DoubleArgFunction f = new DoubleArgFunction() {
			@Override
			public double computeValue(double x, double y) {
				return 1; 
			}
		};
		long time = treeBuilder.buildTree(128, -1, 1, 2, f);
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		double [][] matrix;
		double [] rhs;
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(128, 0, 0, 2,f);
		
		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();
		
		long t1 = System.currentTimeMillis();
		
		MatrixUtils.eliminate(rhs.length, matrix, rhs);
		MatrixUtils.backwardSubstitution(matrix, rhs, matrix.length-1);

		long t2 = System.currentTimeMillis();
		
		System.out.println("Productions: "+(time/1000.0)+" s");
		System.out.println("Classic: "+(t2-t1)/1000.0+" s");
		
	}
}
