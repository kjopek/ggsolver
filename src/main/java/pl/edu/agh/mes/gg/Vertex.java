/**
 * @(#)Vertex.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg;

import java.util.Map;

public class Vertex {
	//constructor
	public Vertex(Vertex Left, Vertex Right, Vertex Parent, String Lab){
		this.m_left=Left;
		this.m_right=Right;
		this.m_parent=Parent;
		this.m_label=Lab;
		m_a = new double[17][17];
		m_b = new double[17];
	}
	//empty constructor
	public Vertex(){
		this.m_left=null;
		this.m_right=null;
		this.m_parent=null;
		m_a = new double[17][17];
		m_b = new double[17];
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
	
	public void addCoefficients(Map<Integer, Double> solution, int firstNodeNr){
		
		
		if(orig_rhs != null){
			double[] rhs = orig_rhs;
			if(firstNodeNr == 0){
				solution.put(1, rhs[0]);
				solution.put(3, rhs[1]);
				solution.put(5, rhs[2]);
				solution.put(7, rhs[3]);
				solution.put(0, rhs[4]);
				solution.put(2, rhs[5]);
				solution.put(4, rhs[6]);
				solution.put(6, rhs[7]);
				for(int i = 8; i<21; i++){
					solution.put(i, rhs[i]);
				}
			}
			else{
				solution.put(firstNodeNr+17, rhs[0]);
				solution.put(firstNodeNr+18, rhs[1]);
				solution.put(firstNodeNr+19, rhs[2]);
				solution.put(firstNodeNr+20, rhs[3]);

				for(int i = firstNodeNr; i<firstNodeNr + 17; i++){
					solution.put(i, rhs[i - firstNodeNr + 4]);
				}
			}
		}
		else{
			for(int i = firstNodeNr; i<firstNodeNr + 17; i++){
				solution.put(i,m_b[i - firstNodeNr]);
			}
		}
	}
	
}

