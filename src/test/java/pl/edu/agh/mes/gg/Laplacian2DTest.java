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
		double botLeftX = 0.0;
		double botLeftY = 0.0;
		double step = 0.20;
		double[] botLeftCoord = new double[] { botLeftX, botLeftY };
		int nrOfTiers = 2; 
		TreeBuilder treeBuilder = new TreeBuilder();
		NeumanBoundaryCondition neumanBoundaryCondition = new NeumanBoundaryCondition(botLeftCoord, size) {
			
			@Override
			public double computeValueOnTopEdge(double x, double y) {
				return 0;
			}
			
			@Override
			public double computeValueOnRightEdge(double x, double y) {
				return -2; 
			}
			
			@Override
			public double computeValueOnLeftEdge(double x, double y) {
				return 0;
			}
			
			@Override
			public double computeValueOnBotEdge(double x, double y) {
				return 2; 
			}
		};
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, botLeftCoord[0], botLeftCoord[1], size, null, neumanBoundaryCondition);
		
		double [][] matrix = matrixGenerator.getMatrix();
		double [] rhs = matrixGenerator.getRhs();
		
		DirichletBoundaryCondition dirichletBoundaryCondition = new DirichletBoundaryCondition();
		dirichletBoundaryCondition.applyDirichletBoundaryCondition(matrix, rhs, tierList, new int[] {0,1,2,3,4,5,6,7,8}, 0);

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
			//System.out.println("key: "+key);
			//System.out.println("Matrix: "+matrixSolution.get(key));
			//System.out.println("Production: "+productionSolution.get(key));
			assertTrue(Math.abs(matrixSolution.get(key) - productionSolution.get(key)) < epsilon);
		}
		
		assertTrue(productionSolution.size() == matrixSolution.size());
		
		int solutionMatrixSize = (int)Math.ceil(size / step) + 1;
		double[][] solutionMatrix = new double[solutionMatrixSize][solutionMatrixSize];
		Map<double[],int[]> pointIdicesMap = new HashMap<double[],int[]>();
		double xCoord = botLeftX;
		double yCoord = botLeftY;
		int iIndex = 0; 
		int jIndex = solutionMatrixSize - 1; 
		
		for(int i = 0; i<solutionMatrixSize; i++){
			yCoord = botLeftY;
			jIndex = solutionMatrixSize - 1;
			for(int j = 0; j<solutionMatrixSize; j++){
				pointIdicesMap.put(new double[]{xCoord,yCoord}, new int[]{iIndex,jIndex});
				yCoord += step;
				jIndex -= 1; 
			}
			iIndex += 1; 
			xCoord += step;
		}


		for(double[] key : pointIdicesMap.keySet()){
			System.out.println(key[0] + " " + key[1] + " " +pointIdicesMap.get(key)[0] + " " + pointIdicesMap.get(key)[1]);
		}

		for(Tier tier : tierList){
			tier.getSolution(pointIdicesMap, solutionMatrix);
		}
		
		System.out.println("\nSOLUTION\n");
		for(int i = 0; i<solutionMatrixSize; i++){
			for(int j = 0; j<solutionMatrixSize; j++){
				System.out.print(String.format(null,"% .5f ", new Object[]{solutionMatrix[i][j]}) + " ");
			}
			System.out.println();
		}


		
		
	}

}
