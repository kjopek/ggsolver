package pl.edu.agh.mes.gg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.Direction;
import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;

import org.junit.Test;

import pl.edu.agh.mes.gg.twod.A1;
import pl.edu.agh.mes.gg.twod.A2;
import pl.edu.agh.mes.gg.twod.AN;
import pl.edu.agh.mes.gg.twod.BS;
import pl.edu.agh.mes.gg.twod.ERoot;

public class SolverTest2DSequential extends Thread {

	public SolverTest2DSequential() {
	}

	@Test
	public void testSolveMES2D(){
		int nrOfTiers = 2;
		final double epsilon = 1e-7;

		DoubleArgFunction f = new DoubleArgFunction(){

			@Override
			public double computeValue(double x, double y, Direction direction) {
				return 1.0;
			}
			
		};
		
		double [][] matrix;
		double [] rhs;
		
		MatrixGenerator matrixGenerator = new MatrixGenerator(); 
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, -1, -1, 1,f,null);
		
		matrix = matrixGenerator.getMatrix();
		rhs = matrixGenerator.getRhs();
		
		Map<Integer,Double> matrixSolution = MatrixUtils.getSolutionThroughBackwardSubstitution(matrix, rhs);
		
		
		Counter counter = new Counter(this);

		/* production tree:
		 * 
		 *          _____p1_____
		 *         /            \
		 *       p3a            p3b
		 */
		
		Vertex S = new Vertex(null,null,null,"S");

		P1 p1 = new P1(S,counter);
		p1.start();
		counter.release();
		
		//p2^{a,b}
		P3 p3a = new P3(p1.m_vertex.m_left,counter);
		P3 p3b = new P3(p1.m_vertex.m_right,counter);
		p3a.start();
		p3b.start();
		counter.release();
		
		/* generating matrix here? */
		
		A1 a1 = new A1(p3a.m_vertex, counter, tierList.get(0));
		a1.start();
		counter.release();
		AN a2 = new AN(p3b.m_vertex, counter, tierList.get(1));
		a2.start();
		counter.release();

		A2 a2_1 = new A2(p1.m_vertex, counter);
		a2_1.start();

		counter.release();

		ERoot eroot = new ERoot(p1.m_vertex, counter);
		eroot.start();
		counter.release();
		
		BS bs = new BS(p1.m_vertex, counter);
		bs.start();
		counter.release();
		
		BS bs1 = new BS(p3a.m_vertex, counter);
		bs1.start();
		counter.release();
		
		BS bs2 = new BS(p3b.m_vertex, counter);
		bs2.start();
		counter.release();
		
		
		Map<Integer,Double> productionSolution = new HashMap<Integer, Double>();
		p3a.m_vertex.addCoefficients(productionSolution, 0);
		p3b.m_vertex.addCoefficients(productionSolution, 16);
		
		for(Tier tier : tierList){
			tier.setCoefficients(productionSolution);
			tier.checkInterpolationCorectness(f);
		}
		
		for(int key : matrixSolution.keySet()) {
				System.out.println("key: "+key);
				System.out.println("Matrix: "+matrixSolution.get(key));
				System.out.println("Production: "+productionSolution.get(key));
    			//assertTrue(Math.abs(matrixSolution.get(key) - productionSolution.get(key)) < epsilon);
		}
		
		//MatrixUtils.printMatrix(p3a.m_vertex.m_a, p3a.m_vertex.m_b);
		
	}

}
