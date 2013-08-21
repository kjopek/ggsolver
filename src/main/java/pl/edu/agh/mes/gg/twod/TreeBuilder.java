package pl.edu.agh.mes.gg.twod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrixgeneration.DoubleArgFunction;
import matrixgeneration.MatrixGenerator;
import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.P1;
import pl.edu.agh.mes.gg.P2;
import pl.edu.agh.mes.gg.P3;
import pl.edu.agh.mes.gg.Vertex;

public class TreeBuilder extends Thread {
	public List<Vertex> buildTree(List<Tier> tierList) {

		List<Vertex> returnList = new ArrayList<Vertex>();
		Map<Integer, List<Vertex>> levels = new HashMap<Integer, List<Vertex>>();
		boolean added = true;
		int lastLevel = 1;
		
		Vertex S = new Vertex(null,null,null,"S");
		Counter counter = new Counter(this);
		
		
		P1 p1 = new P1(S,counter);
		p1.start();
		counter.release();
		
		counter.inc();

		recursiveTreeBuilder(tierList.size(), S, counter, this, 0, tierList.size()-1, tierList);
		
		counter.release();
		
		A2 rootA2 = new A2(S, counter);
		rootA2.start();
		counter.release();
		
		ERoot eroot = new ERoot(S, counter);
		eroot.start();
		
		counter.release();

		BS bs = new BS(S, counter);
		bs.start();
		counter.release();
		
		counter.inc();
		parallelBS(S, counter, this);
		counter.release();
		
		levels.put(1, new ArrayList<Vertex>());
		levels.get(1).add(S);
		
		while (added) {
			added = false;
			List<Vertex> currentLevel = new ArrayList<Vertex>();
			for (Vertex v : levels.get(lastLevel)) {
				// check left son
				if (v.m_left != null) {
					added = true;
					currentLevel.add(v.m_left);
				}
				if (v.m_right != null) {
					added = true;
					currentLevel.add(v.m_right);
				}
			}
			if (added) {
				levels.put(lastLevel+1, currentLevel);
			}
			lastLevel++;
		}

		int maxLevel = 1;
		
		for (int i : levels.keySet()) {
			if (i>maxLevel) {
				maxLevel = i;
			}
		}
		
		for (Vertex v : levels.get(maxLevel-1)) {
			if (v.m_left == null && v.m_right == null) {
				returnList.add(v);
			} else {
				returnList.add(v.m_left);
				returnList.add(v.m_right);
			}
		}
		
		return returnList;
		
	}
	
	private void recursiveTreeBuilder(final int n, Vertex parent, 
			final Counter parentCounter, final Thread thread,
			final int low_range, final int high_range, final List<Tier> tierList) {
				
		if (high_range-low_range+1>3) {
			final Counter counter = new Counter(thread);
			final P2 p2a = new P2(parent.m_left, counter);
			final P2 p2b = new P2(parent.m_right, counter);
			
			p2a.start();
			p2b.start();
			counter.release();
			
			counter.inc();
			counter.inc();

			new Thread() {
				public void run() {
					recursiveTreeBuilder(n, p2a.m_vertex, counter, this, 
							low_range, low_range+(high_range-low_range)/2, tierList);
				}
			}.start();

			new Thread() {
				public void run() {
					recursiveTreeBuilder(n, p2b.m_vertex, counter, this, 
							low_range+(high_range-low_range)/2+1, high_range, tierList);
				}
			}.start();
			
			counter.release();
			
			A2 a2a = new A2(p2a.m_vertex, counter);
			A2 a2b = new A2(p2b.m_vertex, counter);
			a2a.start();
			a2b.start();
			counter.release();
			
			E e1 = new E(p2a.m_vertex, counter);
			E e2 = new E(p2b.m_vertex, counter);
			e1.start();
			e2.start();
			counter.release();
			
			parentCounter.dec();
			
		}	else if (high_range-low_range+1== 3) {
			Counter counter = new Counter(thread);
			P3 p3 = new P3(parent.m_left, counter);
			P2 p2 = new P2(parent.m_right, counter);
			
			p3.start();
			p2.start();
			counter.release();
			
			P3 p3a = new P3(p2.m_vertex.m_left, counter);
			P3 p3b = new P3(p2.m_vertex.m_right, counter);
			p3a.start();
			p3b.start();
			counter.release();
			
			if (low_range == 0) {
				A1 a1 = new A1(p3.m_vertex, counter, tierList.get(low_range));
				a1.start();
			} else {
				A a = new A(p3.m_vertex, counter, tierList.get(low_range));
				a.start();
			}
			
			if (high_range == n-1) {
				AN an = new AN(p2.m_vertex.m_right, counter, tierList.get(high_range));
				an.start();
			} else {
				A  a = new A(p2.m_vertex.m_right, counter, tierList.get(high_range));
				a.start();
			}
			
			A a = new A(p2.m_vertex.m_left, counter,tierList.get(low_range+1));
			a.start();
			
			counter.release();
			
			A2 a2 = new A2(p2.m_vertex, counter);
			a2.start();
			counter.release();
			
			E e1 = new E(p2.m_vertex, counter);
			e1.start();
			counter.release();
			
			parentCounter.dec();
			
		}	else if (high_range-low_range+1==2) {
			
			Counter counter = new Counter(thread);
			P3 p3a = new P3(parent.m_left, counter);
			P3 p3b = new P3(parent.m_right, counter);

			p3a.start();
			p3b.start();
			counter.release();
			
			if (low_range == 0) {
				A1 a1 = new A1(p3a.m_vertex, counter, tierList.get(low_range));
				a1.start();
			} else {
				A a = new A(p3a.m_vertex, counter, tierList.get(low_range));
				a.start();
			}
			
			if (high_range == n-1) {
				AN an = new AN(p3b.m_vertex,counter, tierList.get(high_range));
				an.start();
			} else {
				A a = new A(p3b.m_vertex, counter, tierList.get(high_range));
				a.start();
			}
			
			counter.release();
			
			parentCounter.dec();

		}
	}
	
	private void parallelBS(final Vertex parent, final Counter parentCounter, Thread thread) {
		if (parent != null) {
			final Counter counter = new Counter(thread);
			if (parent.m_left != null) {
				BS bs1 = new BS(parent.m_left, counter);
				bs1.start();
			}
			
			if (parent.m_right != null) {
				BS bs2 = new BS(parent.m_right, counter);
				bs2.start();
			}
			
			counter.release();
			
			counter.inc();
			counter.inc();
				new Thread() {
					public void run() {
						parallelBS(parent.m_left, counter, this);
					}
				}.start();
				new Thread() {
					public void run() {
						parallelBS(parent.m_right, counter, this);
					}
				}.start();
			counter.release();
		}
		
		parentCounter.dec();
		
	}
	
}