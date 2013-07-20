/**
 * @(#)Vertex.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg;

public class Vertex {
	//constructor
	public Vertex(Vertex Left, Vertex Right, Vertex Parent, String Lab){
		this.m_left=Left;
		this.m_right=Right;
		this.m_parent=Parent;
		this.m_label=Lab;
		m_a = new double[21][21];
		m_b = new double[21];
	}
	//empty constructor
	public Vertex(){
		this.m_left=null;
		this.m_right=null;
		this.m_parent=null;
		m_a = new double[21][21];
		m_b = new double[21];
	}
	//label
	public String m_label;
	//links to adjacent elements
	public Vertex m_left;
	public Vertex m_right;
	public Vertex m_parent;
	
	//local system of equations
	public double[][] m_a;
	public double[] m_b;
	public double[][] orig_matrix;
	public double[] orig_rhs;

	//methods for adding links
	public void set_left(Vertex Left){
		m_left=Left;
	}
	public void set_right(Vertex Right){
		m_right=Right;
	}
	public void set_parent(Vertex Parent){
		m_parent=Parent;
	}
	public void set_label(String Lab){
		m_label=Lab;
	}
}

