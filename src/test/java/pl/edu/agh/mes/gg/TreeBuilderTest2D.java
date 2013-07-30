package pl.edu.agh.mes.gg;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.Direction;
import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

import pl.edu.agh.mes.gg.twod.TreeBuilder;

public class TreeBuilderTest2D {
	
	final static double epsilon = 1e-9;
	
	@Test
	public void treeBuilderTest() {
		
		int nrOfTiers = 2; 
		TreeBuilder treeBuilder = new TreeBuilder();
		DoubleArgFunction f = new DoubleArgFunction() {
			@Override
			public double computeValue(double x, double y, Direction direction) {
				return x+y+3*x*y; 
			}
		};

		double [][] matrix;
		double [] rhs;

		MatrixGenerator matrixGenerator = new MatrixGenerator();
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, -1, -1, 2, f, null);
		
		List<Vertex> leafVertexList = treeBuilder.buildTree(tierList);

		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();
		
		Map<Integer,Double> matrixSolution = MatrixUtils.getSolutionThroughBackwardSubstitution(matrix, rhs);
				
		Map<Integer,Double> productionSolution = new HashMap<Integer, Double>();
		leafVertexList.get(0).addCoefficients(productionSolution, 0);
		int firstNodeNr = 16; 
		
		for(int i = 1; i< leafVertexList.size(); i++){
			leafVertexList.get(i).addCoefficients(productionSolution, firstNodeNr);
			firstNodeNr+=12;
		}

		for(Tier tier : tierList){
			tier.setCoefficients(productionSolution);
			tier.checkInterpolationCorectness(f);
		}
		
		for(int key : matrixSolution.keySet()) {
			System.out.println("key: "+key);
			System.out.println("Matrix: "+matrixSolution.get(key));
			System.out.println("Production: "+productionSolution.get(key));
			assertTrue(Math.abs(matrixSolution.get(key) - productionSolution.get(key)) < epsilon);
		}
		
		assertTrue(productionSolution.size() == matrixSolution.size());
		
	}
	

}
