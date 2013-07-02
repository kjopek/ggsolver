/**
 * @(#)Production.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg;

import matrixgeneration.Tier;


public abstract class Production extends Thread {

	public Production(Vertex Vert,Counter Count){
		m_vertex = Vert;
		m_counter = Count;
		m_drawer = new GraphDrawer();
		m_tier = null;
		/* increment counter - main thread should wait for finish of all productions 
		 * before continue */
		m_counter.inc();
	}
	
	public Production(Vertex Vert, Counter Count, Tier tier) {
		m_vertex = Vert;
		m_counter = Count;
		m_drawer = new GraphDrawer();
		m_tier = tier;
		/* increment counter - main thread should wait for finish of all productions 
		 * before continue */
		m_counter.inc();

	}
	

	//returns first vertex from the left
	public abstract Vertex apply(Vertex v);

	//run the thread
	public void run() {
		//apply the production
		m_vertex = apply(m_vertex);
		//plot the graph
		//m_drawer.draw(m_vertex);
		m_counter.dec();
	}

	//vertex where the production will be applied
	public Vertex m_vertex;
	//graph drawer
	public GraphDrawer m_drawer;
	//productions counter
	public Counter m_counter;
	// for mes2d - Tier
	public Tier m_tier;
}

