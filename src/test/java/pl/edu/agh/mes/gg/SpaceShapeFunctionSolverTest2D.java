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
		int nrOfTiers = 8;
		final double epsilon = 1e-7;

		DoubleArgFunction f = new DoubleArgFunction(){

			@Override
			public double computeValue(double x, double y) {
				return (1-x)*(1-y); 
			}
			
		};
		
		MatrixGenerator matrixGenerator = new MatrixGenerator(); 
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, -255, 0, 256,f);
		
		Counter counter = new Counter(this);

		/* production tree:
		 * 
		 *          _____p1_____
		 *         /            \
		 *       p2a            p2b
		 *      /   \          /   \
		 *     /     \        /     \
		 *   p2c     p2d     p2e    p2f
		 *   / \     / \    /  \    /  \
		 * p3a p3b p3c p3d p3e p3f p3g p3h 
		 */
		
		Vertex S = new Vertex(null,null,null,"S");

		P1 p1 = new P1(S,counter);
		p1.start();
		counter.release();
		
		//p2^{a,b}
		P2 p2a = new P2(p1.m_vertex.m_left,counter);
		P2 p2b = new P2(p1.m_vertex.m_right,counter);
		p2a.start();
		p2b.start();
		counter.release();

		//p2^{c,d,e,f}
		P2 p2c = new P2(p2a.m_vertex.m_left, counter);
		P2 p2d = new P2(p2a.m_vertex.m_right, counter);
		P2 p2e = new P2(p2b.m_vertex.m_left, counter);
		P2 p2f = new P2(p2b.m_vertex.m_right, counter);
		p2c.start();
		p2d.start();
		p2e.start();
		p2f.start();		
		counter.release();

		//p3^4
		P3 p3a = new P3(p2c.m_vertex.m_left,counter);
		P3 p3b = new P3(p2c.m_vertex.m_right,counter);
		P3 p3c = new P3(p2d.m_vertex.m_left,counter);
		P3 p3d = new P3(p2d.m_vertex.m_right,counter);

		P3 p3e = new P3(p2e.m_vertex.m_left,counter);
		P3 p3f = new P3(p2e.m_vertex.m_right,counter);
		P3 p3g = new P3(p2f.m_vertex.m_left,counter);
		P3 p3h = new P3(p2f.m_vertex.m_right,counter);
		p3a.start();
		p3b.start();
		p3c.start();
		p3d.start();		
		p3e.start();
		p3f.start();
		p3g.start();
		p3h.start();
		counter.release();
		
		
		A1 a1 = new A1(p3a.m_vertex, counter, tierList.get(0));
		A a2 = new A(p3b.m_vertex, counter, tierList.get(1));
		A a3 = new A(p3c.m_vertex, counter, tierList.get(2));
		A a4 = new A(p3d.m_vertex, counter, tierList.get(3));
		A a5 = new A(p3e.m_vertex, counter, tierList.get(4));
		A a6 = new A(p3f.m_vertex, counter, tierList.get(5));
		A a7 = new A(p3g.m_vertex, counter, tierList.get(6));
		AN a8 = new AN(p3h.m_vertex, counter, tierList.get(7));
		
		a1.start();
		a2.start();
		a3.start();
		a4.start();
		a5.start();
		a6.start();
		a7.start();
		a8.start();

		counter.release();

		A2 a2_1 = new A2(p2c.m_vertex, counter);
		A2 a2_2 = new A2(p2d.m_vertex, counter);
		A2 a2_3 = new A2(p2e.m_vertex, counter);
		A2 a2_4 = new A2(p2f.m_vertex, counter);
		
		a2_1.start();
		a2_2.start();
		a2_3.start();
		a2_4.start();
		
		counter.release();
		
		E e1 = new E(p2c.m_vertex, counter);
		E e2 = new E(p2d.m_vertex, counter);
		E e3 = new E(p2e.m_vertex, counter);
		E e4 = new E(p2f.m_vertex, counter);
		e1.start();
		e2.start();
		e3.start();
		e4.start();
		counter.release();
		
		A3 a3_1 = new A3(p2a.m_vertex, counter);
		A3 a3_2 = new A3(p2b.m_vertex, counter);
		a3_1.start();
		a3_2.start();
		
		counter.release();
		
		E e5 = new E(p2a.m_vertex, counter);
		E e6 = new E(p2b.m_vertex, counter);
		e5.start();
		e6.start();
		
		counter.release();
		
		A3 a3_3 = new A3(p1.m_vertex, counter);
		a3_3.start();
		counter.release();
		
		ERoot eroot = new ERoot(p1.m_vertex, counter);
		eroot.start();
		counter.release();
		
		BS bs = new BS(p1.m_vertex, counter);
		bs.start();
		counter.release();
		
		BS bs1 = new BS(p2a.m_vertex, counter);
		BS bs2 = new BS(p2b.m_vertex, counter);
		bs1.start();
		bs2.start();
		counter.release();
		
		BS bs3 = new BS(p2c.m_vertex, counter);
		BS bs4 = new BS(p2d.m_vertex, counter);
		BS bs5 = new BS(p2e.m_vertex, counter);
		BS bs6 = new BS(p2f.m_vertex, counter);
		
		bs3.start();
		bs4.start();
		bs5.start();
		bs6.start();

		counter.release();
		
		BS bs7 = new BS(p3a.m_vertex, counter);
		BS bs8 = new BS(p3b.m_vertex, counter);
		BS bs9 = new BS(p3c.m_vertex, counter);
		BS bs10 = new BS(p3d.m_vertex, counter);
		BS bs11 = new BS(p3e.m_vertex, counter);
		BS bs12 = new BS(p3f.m_vertex, counter);
		BS bs13 = new BS(p3g.m_vertex, counter);
		BS bs14 = new BS(p3h.m_vertex, counter);
		
		bs7.start();
		bs8.start();
		bs9.start();
		bs10.start();		
		bs11.start();
		bs12.start();
		bs13.start();
		bs14.start();
		
		counter.release();
		
		Map<Integer, Double> solution =
				MatrixUtils.getSolutionThroughBackwardSubstitution(matrixGenerator.getMatrix(), matrixGenerator.getRhs());
		for(Tier tier : tierList){
			tier.setCoefficients(solution);
			//tier.checkInterpolationCorectness(f);
		}
		
		Map<Integer, Double> alternativeSolutionMap = new HashMap<Integer, Double>(); 
		bs7.addCoefficients(alternativeSolutionMap, 0);
		bs8.addCoefficients(alternativeSolutionMap, 5);
		bs9.addCoefficients(alternativeSolutionMap, 8);
		bs10.addCoefficients(alternativeSolutionMap, 11);
		bs11.addCoefficients(alternativeSolutionMap, 14);
		bs12.addCoefficients(alternativeSolutionMap, 17);
		bs13.addCoefficients(alternativeSolutionMap, 20);
		bs14.addCoefficients(alternativeSolutionMap, 23);
		
		MatrixUtils.printMatrix(matrixGenerator.getMatrix(), matrixGenerator.getRhs());
		
		if(alternativeSolutionMap.size() == solution.size()){
			for(int key : alternativeSolutionMap.keySet()){
				if(solution.containsKey(key) && Math.abs((solution.get(key) - alternativeSolutionMap.get(key))) < 0.000001 ){
					System.out.println("ok " + key + " " + solution.get(key) + " " + alternativeSolutionMap.get(key));
					continue;
				}
				else{
					System.out.println("zle " + key + " " + solution.get(key) + " " + alternativeSolutionMap.get(key));
				}
				//throw new RuntimeException("wrong coefficient " + key + " " + ((solution.get(key) - alternativeSolutionMap.get(key))));
			}
		}
		else
			throw new RuntimeException("wrong solution !");
		
		
	}

}
