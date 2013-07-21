package pl.edu.agh.mes.gg;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

import pl.edu.agh.mes.gg.twod.A;
import pl.edu.agh.mes.gg.twod.A1;
import pl.edu.agh.mes.gg.twod.A2;
import pl.edu.agh.mes.gg.twod.A3;
import pl.edu.agh.mes.gg.twod.AN;
import pl.edu.agh.mes.gg.twod.BS;
import pl.edu.agh.mes.gg.twod.E;
import pl.edu.agh.mes.gg.twod.ERoot;

public class SpaceShapeFunctionSolverTest2D extends Thread {

	public SpaceShapeFunctionSolverTest2D() {
	}

	@Test
	public void testSolveMES2D(){
		int nrOfTiers = 4;
		final double epsilon = 1e-7;

		DoubleArgFunction f = new DoubleArgFunction(){

			@Override
			public double computeValue(double x, double y) {
				return  (1-x)*x*y + (1-x)*(1-y)+ x*y*(1-y) + 4*(1-x)*y*(1-y) + (1-x)*(1-y)*x*y + (1-x)*(x)*(1-y);
			}
			
		};
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		System.out.println("przed");
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, 0, 0, 2,f);
		System.out.println("po");
		
		MatrixUtils.printMatrix(matrixGenerator.getMatrix(), matrixGenerator.getRhs());
		
		Map<Integer, Double> solution =
				MatrixUtils.getSolutionThroughBackwardSubstitution(matrixGenerator.getMatrix(), matrixGenerator.getRhs());
		
		for(Map.Entry<Integer, Double> entry : solution.entrySet()){
			if(Math.abs(entry.getValue()) > 0.001)
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		for(Tier tier : tierList){
			tier.setCoefficients(solution);
			tier.checkInterpolationCorectness(f);
		}
		/*
		Map<Integer, Double> alternativeSolutionMap = new HashMap<Integer, Double>(); 
		bs7.addCoefficients(alternativeSolutionMap, 0);
		bs8.addCoefficients(alternativeSolutionMap, 5);
		bs9.addCoefficients(alternativeSolutionMap, 8);
		bs10.addCoefficients(alternativeSolutionMap, 11);
		bs11.addCoefficients(alternativeSolutionMap, 14);
		bs12.addCoefficients(alternativeSolutionMap, 17);
		bs13.addCoefficients(alternativeSolutionMap, 20);
		bs14.addCoefficients(alternativeSolutionMap, 23);
		

		if(alternativeSolutionMap.size() == solution.size()){
			for(int key : alternativeSolutionMap.keySet()){
				if(solution.containsKey(key) && Math.abs((solution.get(key) - alternativeSolutionMap.get(key))) < 0.000001 ){
					System.out.println("ok " + key + " " + solution.get(key) + " " + alternativeSolutionMap.get(key));
				}
				else{
					System.out.println("zle " + key + " " + solution.get(key) + " " + alternativeSolutionMap.get(key));
				}
				//throw new RuntimeException("wrong coefficient " + key + " " + ((solution.get(key) - alternativeSolutionMap.get(key))));
			}
		}
		else
			throw new RuntimeException("wrong solution !");
		
		*/
	}

}
