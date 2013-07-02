/**
 * @(#)Executor.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg.oned;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.GraphDrawer;
import pl.edu.agh.mes.gg.P1;
import pl.edu.agh.mes.gg.P2;
import pl.edu.agh.mes.gg.P3;
import pl.edu.agh.mes.gg.Vertex;


class Executor extends Thread {
	public synchronized void run() {

	  	Counter counter = new Counter(this);
		//axiom
		Vertex S = new Vertex(null,null,null,"S");

		//p1
		P1 p1 = new P1(S,counter);
		p1.start();
		counter.release();

		//p2a,p2b
		P2 p2a = new P2(p1.m_vertex.m_left,counter);
		P2 p2b = new P2(p1.m_vertex.m_right,counter);
		p2a.start();
		p2b.start();		
		counter.release();

		//p2^2,p3^2
		P2 p2c = new P2(p2a.m_vertex.m_left,counter);
		P2 p2d = new P2(p2a.m_vertex.m_right,counter);
		P3 p3a = new P3(p2b.m_vertex.m_left,counter);
		P3 p3b = new P3(p2b.m_vertex.m_right,counter);
		p2c.start();
		p2d.start();
		p3a.start();
		p3b.start();
		counter.release();

		//p3^4
		P3 p3c = new P3(p2c.m_vertex.m_left,counter);
		P3 p3d = new P3(p2c.m_vertex.m_right,counter);
		P3 p3e = new P3(p2d.m_vertex.m_left,counter);
		P3 p3f = new P3(p2d.m_vertex.m_right,counter);
		p3c.start();
		p3d.start();
		p3e.start();
		p3f.start();
		counter.release();
		
		A1 a1a = new A1(p3c.m_vertex, counter);
		A aa = new A(p3d.m_vertex, counter);
		A ab = new A(p3e.m_vertex, counter);
		A ac = new A(p3f.m_vertex, counter);
		A ad = new A(p3a.m_vertex, counter);
		AN ana = new AN(p3b.m_vertex, counter);		
		a1a.start();
		aa.start();
		ab.start();
		ac.start();
		ad.start();
		ana.start();
		counter.release();
		
		A2 a2a = new A2(p2b.m_vertex, counter);
		A2 a2b = new A2(p2c.m_vertex, counter);
		A2 a2c = new A2(p2d.m_vertex, counter);
		
		a2a.start(); 
		a2b.start(); 
		a2c.start();
		counter.release();

		E2 e2g = new E2(p2b.m_vertex, counter);
		E2 e2h = new E2(p2c.m_vertex, counter);
		E2 e2i = new E2(p2d.m_vertex, counter);
		e2g.start();
		e2h.start();
		e2i.start();
		counter.release();
		

		A2 a2d = new A2(p2a.m_vertex, counter);
		a2d.start();
		counter.release();
		
		E2 e2j = new E2(a2d.m_vertex, counter);
		e2j.start();
		counter.release();
		
		A2 a2e = new A2(S, counter);
		a2e.start();
		counter.release();
		
		ERoot eroota = new ERoot(S,counter);
		eroota.start(); 
		counter.release();
		
		
		BS bsa = new BS(S, counter);
		bsa.start();
		counter.release();
		
		BS bsb = new BS(S.m_left, counter);
		BS bsc = new BS(S.m_right, counter);
		bsb.start(); 
		bsc.start();
		counter.release();
		
		BS bsd = new BS(S.m_left.m_left, counter);
		BS bse = new BS(S.m_left.m_right, counter);
		bsd.start();
		bse.start();
		counter.release();
		
		//done
		System.out.println("done");
		GraphDrawer drawer = new GraphDrawer();
		drawer.draw(p1.m_vertex);
		System.out.println("---------------------");
		
		System.out.println(bsa.m_vertex.m_b[0]);
		System.out.println(bsa.m_vertex.m_b[1]);
		System.out.println(bsa.m_vertex.m_b[2]);
		System.out.println(bsb.m_vertex.m_b[0]);
		System.out.println(bsb.m_vertex.m_b[1]);
		System.out.println(bsb.m_vertex.m_b[2]);
		System.out.println(bsc.m_vertex.m_b[0]);
		System.out.println(bsc.m_vertex.m_b[1]);
		System.out.println(bsc.m_vertex.m_b[2]);
		System.out.println(bsd.m_vertex.m_b[0]);
		System.out.println(bsd.m_vertex.m_b[1]);
		System.out.println(bsd.m_vertex.m_b[2]);
		System.out.println(bsc.m_vertex.m_b[2]);
		System.out.println(bse.m_vertex.m_b[0]);
		System.out.println(bse.m_vertex.m_b[1]);
		System.out.println(bse.m_vertex.m_b[2]);
	}
}

/*
class Executor extends Thread {
	public synchronized void run() {

	  	Counter counter = new Counter(this);
		//axiom
		Vertex S = new Vertex(null,null,null,"S");

		//p1
		P1 p1 = new P1(S,counter);
		p1.start();

		counter.release();

		//p2
		P2 p2a = new P2(p1.m_vertex.m_left,counter);
		p2a.start();

		counter.release();

		//p2
		P2 p2b = new P2(p1.m_vertex.m_right,counter);
		p2b.start();

		counter.release();

		//p2
		P2 p2c = new P2(p2a.m_vertex.m_left,counter);
		p2c.start();

		counter.release();

		//p2
		P2 p2d = new P2(p2a.m_vertex.m_right,counter);
		p2d.start();

		counter.release();

		//p3
		P3 p3a = new P3(p2b.m_vertex.m_left,counter);
		p3a.start();

		counter.release();

		//p3
		P3 p3b = new P3(p2b.m_vertex.m_right,counter);
		p3b.start();

		counter.release();

		//p3
		P3 p3c = new P3(p2c.m_vertex.m_left,counter);
		p3c.start();

		counter.release();

		//p3
		P3 p3d = new P3(p2c.m_vertex.m_right,counter);
		p3d.start();

		counter.release();

		//p3
		P3 p3e = new P3(p2d.m_vertex.m_left,counter);
		p3e.start();

		counter.release();

		//p3
		P3 p3f = new P3(p2d.m_vertex.m_right,counter);
		p3f.start();

		counter.release();

		//done
		System.out.println("done");
		GraphDrawer drawer = new GraphDrawer();
		drawer.draw(p1.m_vertex);
	}
}
*/

