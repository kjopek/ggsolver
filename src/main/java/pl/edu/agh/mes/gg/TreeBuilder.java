package pl.edu.agh.mes.gg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;


public class TreeBuilder extends Thread {
	private int numbers;
	public List<Vertex> run(int tiers, double botLeftX, double botLeftY, double size, DoubleArgFunction f) {
		MatrixGenerator matrixGenerator = new MatrixGenerator();
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(tiers, botLeftX, botLeftY, size, f);
		
		Map<Integer, List<Production>> levels = new HashMap<Integer, List<Production>>();
		
		Vertex S = new Vertex(null,null,null,"S");
		Counter counter = new Counter(this);

		P1 p1 = new P1(S,counter);
		p1.start();
		counter.release();

		recursiveTreeBuilder(tiers, S, counter, 1, levels);
		
		return null;
	}
	
	private void recursiveTreeBuilder(int n, Vertex parent, Counter counter, int level, Map<Integer, List<Production>> levels) {
		if (n>3) {
			P2 p2a = new P2(parent.m_left, counter);
			P2 p2b = new P2(parent.m_right, counter);

			p2a.start();
			p2b.start();
			counter.release();
			
			if (!levels.containsKey(level)) {
			}
			
			recursiveTreeBuilder(n/2, p2a.m_vertex, counter, level+1, levels);
			recursiveTreeBuilder(n/2+n%2, p2b.m_vertex, counter, level+1, levels);
			
		} else if (n==3) {
			P2 p2 = new P2(parent.m_left, counter);
			p2.start();
			counter.release();
			
			P2 p2a = new P2(p2.m_vertex.m_left, counter);
			P3 p3 = new P3(p2.m_vertex.m_right, counter);
			p2a.start();
			p3.start();
			counter.release();
						
			P3 p3a = new P3(p2a.m_vertex.m_left, counter);
			P3 p3b = new P3(p2a.m_vertex.m_right, counter);
			p3a.start();
			p3b.start();
			counter.release();
			
		}	else if (n==2) {
		
			P3 p3a = new P3(parent.m_left, counter);
			P3 p3b = new P3(parent.m_right, counter);

			p3a.start();
			p3b.start();
			counter.release();

		} else if (n==1) {
			
			P3 p3 = new P3(parent.m_left, counter);
			p3.start();
			counter.release();
			
		}
	}		
}