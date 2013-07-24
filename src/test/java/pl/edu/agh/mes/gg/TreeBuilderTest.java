package pl.edu.agh.mes.gg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

public class TreeBuilderTest {
	public static void main(String args[]) {
		
		int nrOfTiers = 400; 
		TreeBuilder treeBuilder = new TreeBuilder();
		DoubleArgFunction f = new DoubleArgFunction() {
			@Override
			public double computeValue(double x, double y) {
				return 0.5; 
			}
		};
		long productionTime1 = System.currentTimeMillis();
		List<Vertex> leafVertexList = treeBuilder.buildTree(nrOfTiers, -1, 1, 2, f);
		long productionTime2 = System.currentTimeMillis();
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		double [][] matrix;
		double [] rhs;
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, 0, 0, 2,f);
		
		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();
		
		long t1 = System.currentTimeMillis();
		
		MatrixUtils.eliminate(rhs.length, matrix, rhs);
		Map<Integer,Double> matrixSolution = MatrixUtils.getSolutionThroughBackwardSubstitution(matrix, rhs);

		long t2 = System.currentTimeMillis();
		
		System.out.println("Productions: "+((productionTime2 - productionTime1)/1000.0)+" s");
		System.out.println("Classic: "+(t2-t1)/1000.0+" s");
		
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
		for(int key : matrixSolution.keySet()){
			double matrixValue = matrixSolution.get(key);
			if(Math.abs(matrixValue - productionSolution.get(key)) > 0.000001)
				throw new RuntimeException(); 
		}
		if(productionSolution.size() != matrixSolution.size())
			throw new RuntimeException(); 
		
		
	}
}
