package pl.edu.agh.mes.gg.twod;

import java.util.Map;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class BS extends Production{

	public BS(Vertex vertex, Counter counter){
		super(vertex, counter);
	}
	
	@Override
	public Vertex apply(Vertex T) {
		System.out.println("BS");
		if(T.m_parent != null){
			// copy previously calculated variables into rhs
			Vertex parent = T.m_parent;
			if(T.m_parent.m_left == T){
				for (int i=0; i<3; i++) {
					T.m_b[i+3] = parent.m_b[i+3];
					T.m_b[i+6] = parent.m_b[i];
				}
			}
			else{
				for (int i=0; i<3; i++) {
					T.m_b[i+3] = parent.m_b[i];
					T.m_b[i+6] = parent.m_b[i+6];
				}
			}
			
			// lower part of matrix [4:9]x[4:9] should be filled with 1.0 on diagonal and 0.0
			// everywhere else
			
			for (int i=0; i<6;i++) {
				for (int j=0;j<6;j++) {
					if (i==j) {
						T.m_a[i+3][j+3] = 1.0;
					} else {
						T.m_a[i+3][j+3] = 0.0;
					}
				}
			}
		}
		
		// run backward substitution
		
		for (int i=8; i>=0; i--) {
			double sum = T.m_b[i];
			for (int j=8;j>=i+1;j--) {
				sum -= T.m_a[i][j] * T.m_b[j]; 
				// "clean matrix" - this is not obligatory ;-)
				T.m_a[i][j] = 0.0;
			}
			T.m_b[i] = sum / T.m_a[i][i];
		}

		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		
		return T; 
	}
	
	public void addCoefficients(Map<Integer, Double> solution, int firstNodeNr){
		
		for(int i = firstNodeNr; i<firstNodeNr + 6; i++)
			solution.put(firstNodeNr,this.m_vertex.m_b[i - firstNodeNr]); 
	}
	
}
