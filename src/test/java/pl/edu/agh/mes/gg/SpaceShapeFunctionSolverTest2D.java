package pl.edu.agh.mes.gg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.Direction;
import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

import pl.edu.agh.mes.gg.twod.A;
import pl.edu.agh.mes.gg.twod.A1;
import pl.edu.agh.mes.gg.twod.A2;
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
			public double computeValue(double x, double y, Direction direction) {
				return  (1-x)*x*y + (1-x)*(1-y)+ x*y*(1-y) + 4*(1-x)*y*(1-y) + (1-x)*(1-y)*x*y + (1-x)*(x)*(1-y);
			}
			
		};
		
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, 0, 0, 2,f,null);
		
		Map<Integer, Double> solution =
				MatrixUtils.getSolutionThroughBackwardSubstitution(matrixGenerator.getMatrix(), matrixGenerator.getRhs());
		
		
		for(Tier tier : tierList){
			tier.setCoefficients(solution);
			tier.checkInterpolationCorectness(f);
		}
		
	}

}
