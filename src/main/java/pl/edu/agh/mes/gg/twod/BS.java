package pl.edu.agh.mes.gg.twod;

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
		if(T.m_parent != null) {
			Vertex parent = T.m_parent;

			if (T.m_a.length == 15) {
				// copy previously calculated variables into rhs
				if(T.m_parent.m_left == T){
					for (int i=0; i<5; i++) {
						T.m_b[i+5] = parent.m_b[i+5];
						T.m_b[i+10] = parent.m_b[i];
					}
				}
				else{
					for (int i=0; i<5; i++) {
						T.m_b[i+5] = parent.m_b[i];
						T.m_b[i+10] = parent.m_b[i+10];
					}
				}
				
				
				for (int i=0; i<10; i++) {
					for (int j=0; j<10; j++) {
						if (i==j) {
							T.m_a[i+5][j+5] = 1.0;
						} else {
							T.m_a[i+5][j+5] = 0.0;
						}
					}
					// b-s only the first 5 rows
					MatrixUtils.backwardSubstitution(T.m_a, T.m_b, 5);
				}
			} else if (T.m_a.length == 17) {
				// leaf
				for (int i=0; i<5; i++) {
					for (int j=0; j<5; j++) {
						if (i == j) {
							T.m_a[i][j] = 1.0;
							T.m_a[i+12][j] = 1.0;
							T.m_a[i][j+12] = 1.0;
							T.m_a[i+12][j+12] = 1.0;
						} else {
							T.m_a[i][j] = 0.0;
							T.m_a[i+12][j] = 0.0;
							T.m_a[i][j+12] = 0.0;
							T.m_a[i+12][j+12] = 0.0;
						}
					}
					if (T.m_parent.m_left == T) {
						T.m_b[i] = parent.m_b[i+5];
						T.m_b[i+12] = parent.m_b[i];
					} else {
						T.m_b[i] = parent.m_b[i];
						T.m_b[i+12] = parent.m_b[i+10];
					}
				}
				
				MatrixUtils.eliminate(17, T.m_a, T.m_b);

				MatrixUtils.backwardSubstitution(T.m_a, T.m_b, 17);

				if (T.orig_matrix != null) {
					for (int i=0; i<17; i++) {
						for (int j=0; j<17; j++) {
							T.orig_matrix[i+4][j+4] = T.m_a[i][j];
						}
						T.orig_rhs[i+4] = T.m_b[i];
					}
				}
				MatrixUtils.backwardSubstitution(T.m_a, T.m_b, 4);
			}			
			
			
		} else {		
			// run backward substitution on full matrix (ERoot)
			MatrixUtils.backwardSubstitution(T.m_a, T.m_b, 15);
		}

		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		
		return T;
	}
	
}