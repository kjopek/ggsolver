package pl.edu.agh.mes.gg.twod;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class A2 extends Production {

	public A2(Vertex Vert, Counter Count) {
		super(Vert, Count);
	}

	@Override
	public Vertex apply(Vertex T) {
		System.out.println("A2");
		int size = 9;
		int i,j;
		
		T.m_a = new double[size][size];
		T.m_b = new double[size];
		
		
		for (i=0;i<5;i++) {
			for (j=0; j<5; j++) {
				// x:left y:top
				T.m_a[i][j] = T.m_left.m_a[i+5][j+5] + T.m_right.m_a[i][j];

				// x: center y: top
				T.m_a[i][j+5] = T.m_left.m_a[i+5][j];
				
				// x: left y:center
				T.m_a[i+5][j] = T.m_left.m_a[i][j+5];
				
				// x: center y:center
				T.m_a[i+5][j+5] = T.m_left.m_a[i][j];
				
				// x: bottom y: bottom
				T.m_a[i+10][j+10] = T.m_right.m_a[i+5][j+5];

				// x: left y:bottom
				T.m_a[i+10][j] = T.m_right.m_a[i+5][j];
				
				// x: right y: top
				T.m_a[i][j+10] = T.m_right.m_a[i][j+5]; 
				
			}
			T.m_b[i] = T.m_left.m_b[i+5] + T.m_right.m_b[i];
			T.m_b[i+5] = T.m_left.m_b[i];
			T.m_b[i+10] = T.m_right.m_b[i+5];
		}
		
		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}

}
