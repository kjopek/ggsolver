package pl.edu.agh.mes.gg;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.Direction;
import matrixgeneration.DirichletBoundaryCondition;
import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.NeumanBoundaryCondition;
import matrixgeneration.Tier;

import org.junit.Test;

import pl.edu.agh.mes.gg.twod.TreeBuilder;

public class Laplacian2DTest {
	
final static double epsilon = 1e-9;
	

	 

	@Test
	public void laplaceTest() {
		
		double size = 2; 
		double[] botLeftCoord = new double[] { 0.0, 0.0 };
		int nrOfTiers = 2; 
		TreeBuilder treeBuilder = new TreeBuilder();
		NeumanBoundaryCondition neumanBoundaryCondition = new NeumanBoundaryCondition(botLeftCoord, size) {
			
			@Override
			public double computeValueOnTopEdge(double x, double y) {
				return 0;
			}
			
			@Override
			public double computeValueOnRightEdge(double x, double y) {
				return 2; 
			}
			
			@Override
			public double computeValueOnLeftEdge(double x, double y) {
				return 0;
			}
			
			@Override
			public double computeValueOnBotEdge(double x, double y) {
				return -2; 
			}
		};
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, botLeftCoord[0], botLeftCoord[1], size, null, neumanBoundaryCondition);
		
		double [][] matrix = matrixGenerator.getMatrix();
		double [] rhs = matrixGenerator.getRhs();
		
		DirichletBoundaryCondition dirichletBoundaryCondition = new DirichletBoundaryCondition();
		dirichletBoundaryCondition.applyDirichletBoundaryCondition(matrix, rhs, tierList, new int[] {0,1,2,3,4,5,6,7,8}, 0);
		
		/*
		double[] testRhs = new double[37];
		for(int i = 0; i<tierList.get(0).getRhs().length; i++)
			testRhs[i] = tierList.get(0).getRhs()[i];
		
		for(int i = 0; i<tierList.get(1).getRhs().length; i++)
			testRhs[i + 16] += tierList.get(1).getRhs()[i];
		
		for(int i = 0; i<37; i++){
			System.out.println(i + " " + Math.abs(rhs[i] - testRhs[i]));
			assertTrue(Math.abs(rhs[i] - testRhs[i]) < 0.0001);
		}
		System.out.println("koniec RHS");
		double[][] testMatrix = new double[37][37];
		for(int i = 0; i<tierList.get(0).getMatrix().length; i++)
			for(int j = 0; j<tierList.get(0).getMatrix().length; j++)
				testMatrix[i][j] = tierList.get(0).getMatrix()[i][j];
		
		for(int i = 0; i<tierList.get(1).getMatrix().length; i++)
			for(int j = 0; j<tierList.get(1).getMatrix().length; j++)
				testMatrix[i + 16][j + 16] += tierList.get(1).getMatrix()[i][j];
		
		for(int i = 0; i<37; i++)
			for(int j = 0; j<37; j++){
				System.out.println(i + " " + j + " " + Math.abs(matrix[i][j] - testMatrix[i][j]));
				assertTrue(Math.abs(matrix[i][j] - testMatrix[i][j]) < epsilon);
		}
		*/
		
		MatrixUtils.printMatrix(matrix, rhs);
		List<Vertex> leafVertexList = treeBuilder.buildTree(tierList);		
		
				
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
			//tier.checkInterpolationCorectness(f);
		}
		
		 
		
		
		for(int key : matrixSolution.keySet()) {
			System.out.println("key: "+key);
			System.out.println("Matrix: "+matrixSolution.get(key));
			System.out.println("Production: "+productionSolution.get(key));
			//assertTrue(Math.abs(matrixSolution.get(key) - productionSolution.get(key)) < epsilon);
		}
		
		assertTrue(productionSolution.size() == matrixSolution.size());
		
	}

}
