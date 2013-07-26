package pl.edu.agh.mes.gg;

import java.util.List;

import org.junit.Test;

import pl.edu.agh.mes.gg.oned.TreeBuilder;

public class TreeBuilderTest1D {
	
	final static double epsilon = 1e-9;
	
	@Test
	public void treeBuilderTest1D() {
		
		int nrOfTiers = 1000; 

		double[][] matrix = new double[nrOfTiers+1][nrOfTiers+1];
		double[] rhs = new double[nrOfTiers+1];
		
		for (int i=1; i<nrOfTiers; i++) {
			matrix[i][i] = -2.0;
			matrix[i][i-1] = 1.0;
			matrix[i][i+1] = 1.0;
		}
		
		matrix[0][0] = 1.0;
		rhs[0] = 0.0;
		matrix[nrOfTiers][nrOfTiers] = 1.0;
		rhs[nrOfTiers] = 21.0;
		
		long elim1 = System.currentTimeMillis();
		MatrixUtils.eliminate(nrOfTiers+1, matrix, rhs);
		MatrixUtils.backwardSubstitution(matrix, rhs, nrOfTiers);
		long elim2 = System.currentTimeMillis();
		
		TreeBuilder treeBuilder = new TreeBuilder();
		
		long prod1 = System.currentTimeMillis();
		treeBuilder.buildTree(nrOfTiers);
		long prod2 = System.currentTimeMillis();
		
		System.out.println("Elim: "+(elim2-elim1)/1000.0);
		System.out.println("Prod: "+(prod2-prod1)/1000.0);
		
		
	}
	

}
