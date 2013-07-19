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
			if (T.m_a.length == 9) {
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
				

			} else if (T.m_a.length == 6) {

				Vertex parent = T.m_parent;
				
				if(T.m_parent.m_left == T){
					for (int i=0; i<6;i++) {
						for (int j=0;j<6;j++) {
							if (i==j) {
								T.m_a[i][j] = 1.0;
							} else {
								T.m_a[i][j] = 0.0;
							}
						}
					}
					for (int i=0; i<3; i++) {
						T.m_b[i+3] = parent.m_b[i];
						T.m_b[i] = parent.m_b[i+3];
					}
				} else {
					for (int i=0; i<6;i++) {
						for (int j=0;j<6;j++) {
							if (i==j) {
								T.m_a[i][j] = 1.0;
							} else {
								T.m_a[i][j] = 0.0;
							}
						}
					}
					for (int i=0; i<3; i++) {
						T.m_b[i] = parent.m_b[i];
						T.m_b[i+3] = parent.m_b[i+3];
					}
				}
			}
		
		}

		if (T.m_a.length > 6) {

			for (int i=8; i>=0; i--) {
				double sum = T.m_b[i];
				for (int j=8;j>=i+1;j--) {
					sum -= T.m_a[i][j] * T.m_b[j]; 
					// "clean matrix" - this is not obligatory ;-)
					T.m_a[i][j] = 0.0;
				}
				T.m_b[i] = sum / T.m_a[i][i];
			}
		} else {
		
			for (int i=5; i>=0; i--) {
				double sum = T.m_b[i];
				for (int j=5;j>=i+1;j--) {
					sum -= T.m_a[i][j] * T.m_b[j]; 
					// "clean matrix" - this is not obligatory ;-)
					T.m_a[i][j] = 0.0;
				}
				T.m_b[i] = sum / T.m_a[i][i];
			}
		
			if (T.orig_matrix != null) {


				int offset = T.orig_matrix.length - 6;

				System.out.println("Offset = "+offset);

				for (int i=0; i<6; i++) {
					for (int j=0; j<6; j++) {
						T.orig_matrix[i+offset][j+offset] = T.m_a[i][j];
					}
					T.orig_rhs[i+offset] = T.m_b[i];
				}

				for (int i=5+offset; i>=0; i--) {
					double sum = T.orig_rhs[i];
					for (int j=5+offset;j>=i+1;j--) {
						sum -= T.orig_matrix[i][j] * T.orig_rhs[j]; 
						// "clean matrix" - this is not obligatory ;-)
						T.orig_matrix[i][j] = 0.0;
					}
					T.orig_rhs[i] = sum / T.orig_matrix[i][i];
				}
			}
		}
		return T; 
	}
	
	public void addCoefficients(Map<Integer, Double> solution, int firstNodeNr){
		
		for(int i = firstNodeNr; i<firstNodeNr + 6; i++)
			solution.put(firstNodeNr,this.m_vertex.m_b[i - firstNodeNr]); 
	}
	
}

