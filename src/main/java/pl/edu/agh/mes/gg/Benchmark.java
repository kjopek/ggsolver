package pl.edu.agh.mes.gg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.twod.TreeBuilder;

public class Benchmark {

	public static void main(String args[]) {

		int nrOfTiers = 120;

		if (args.length == 1) {
			nrOfTiers = Integer.parseInt(args[0]);
		}
		
		TreeBuilder treeBuilder = new TreeBuilder();
		DoubleArgFunction f = new DoubleArgFunction() {
			@Override
			public double computeValue(double x, double y) {
				return 1.0; 
			}
		};

		MatrixGenerator matrixGenerator = new MatrixGenerator();
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, -1, -1, 2,f);

		long productionTime1 = System.currentTimeMillis();
		treeBuilder.buildTree(tierList);
		long productionTime2 = System.currentTimeMillis();

		double [][] matrix;
		double [] rhs;


		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();

		long eliminationTime1 = System.currentTimeMillis();
		MatrixUtils.getSolutionThroughBackwardSubstitution(matrix, rhs);
		long eliminationTime2 = System.currentTimeMillis();

		System.out.println("Production time: "+(productionTime2-productionTime1)/1000.0+" s");
		System.out.println("Elimination time: "+(eliminationTime2-eliminationTime1)/1000.0+" s");

	}

}
