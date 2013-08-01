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
		
		final int size = 15;
		int i,j;
		int offsetA=0, offsetB=0;

		T.m_a = new double[size][size];
		T.m_b = new double[size];
		/*
		if (T.m_left.m_a.length == T.m_right.m_a.length) {
			if (T.m_left.m_a.length == 17 && T.m_right.m_a.length == 17) {
				offsetA = 12;
				offsetB = 7;
			} else if (T.m_left.m_a.length == 15 && T.m_right.m_a.length == 15) {
				offsetA = 10;
				offsetB = 5;
			}
			System.out.println("OK");
			for (i=0;i<5;i++) {
				for (j=0; j<5; j++) {
					// x:left y:top
					T.m_a[i][j] = T.m_left.m_a[i+offsetA][j+offsetA] + T.m_right.m_a[i+offsetB][j+offsetB];

					// x: center y: top
					T.m_a[i][j+5] = T.m_left.m_a[i+offsetA][j+offsetB];
					
					// x: left y:center
					T.m_a[i+5][j] = T.m_left.m_a[i+offsetB][j+offsetA];
	
					// x: center y:center
					T.m_a[i+5][j+5] = T.m_left.m_a[i+offsetB][j+offsetB];
	
					// x: bottom y: bottom
					T.m_a[i+10][j+10] = T.m_right.m_a[i+offsetA][j+offsetA];
	
					// x: left y:bottom
					T.m_a[i+10][j] = T.m_right.m_a[i+offsetA][j+offsetB];
	
					// x: right y: top
					T.m_a[i][j+10] = T.m_right.m_a[i+offsetB][j+offsetA];
	
				}
				T.m_b[i] = T.m_left.m_b[i+offsetA] + T.m_right.m_b[i+offsetB];
				T.m_b[i+5] = T.m_left.m_b[i+offsetB];
				T.m_b[i+10] = T.m_right.m_b[i+offsetA];
			}
		} else {
			if (T.m_left.m_a.length == 15 && T.m_right.m_a.length == 17) {
				for (i=0; i<5; i++) {
					for (j=0; j<5; j++) {
						// x:left y:top
						T.m_a[i][j] = T.m_left.m_a[i+10][j+10] + T.m_right.m_a[i+7][j+7];
		
						// x: center y: top
						T.m_a[i][j+5] = T.m_left.m_a[i+10][j+5];
						
						// x: left y:center
						T.m_a[i+5][j] = T.m_left.m_a[i+5][j+10];
		
						// x: center y:center
						T.m_a[i+5][j+5] = T.m_left.m_a[i+5][j+5];
		
						// x: bottom y: bottom
						T.m_a[i+10][j+10] = T.m_right.m_a[i+12][j+12];
		
						// x: left y:bottom
						T.m_a[i+10][j] = T.m_right.m_a[i+12][j+7];
		
						// x: right y: top
						T.m_a[i][j+10] = T.m_right.m_a[i+7][j+12];
		
					}
					T.m_b[i] = T.m_left.m_b[i+5] + T.m_right.m_b[i+7];
					T.m_b[i+5] = T.m_left.m_b[i+10];
					T.m_b[i+10] = T.m_right.m_b[i+12];
				}
			} else if (T.m_left.m_a.length == 17 && T.m_right.m_a.length == 15) {
				for (i=0; i<5; i++) {
					for (j=0; j<5; j++) {
						// x:left y:top
						T.m_a[i][j] = T.m_left.m_a[i+12][j+12] + T.m_right.m_a[i+5][j+5];
		
						// x: center y: top
						T.m_a[i][j+5] = T.m_left.m_a[i+12][j+7];
						
						// x: left y:center
						T.m_a[i+5][j] = T.m_left.m_a[i+7][j+12];
		
						// x: center y:center
						T.m_a[i+5][j+5] = T.m_left.m_a[i][j];
		
						// x: bottom y: bottom
						T.m_a[i+10][j+10] = T.m_right.m_a[i+10][j+10];
		
						// x: left y:bottom
						T.m_a[i+10][j] = T.m_right.m_a[i+10][j+5];
		
						// x: right y: top
						T.m_a[i][j+10] = T.m_right.m_a[i+5][j+10];
		
					}
					T.m_b[i] = T.m_left.m_b[i+12] + T.m_right.m_b[i+5];
					T.m_b[i+5] = T.m_left.m_b[i+7];
					T.m_b[i+10] = T.m_right.m_b[i+10];
				}
			}
		}
		*/
		if (T.m_left.m_a.length == T.m_right.m_a.length && T.m_left.m_a.length == 17) {
			for (i=0;i<5;i++) {
				for (j=0; j<5; j++) {
					// x:left y:top
					T.m_a[i][j] = T.m_left.m_a[i+12][j+12] + T.m_right.m_a[i+7][j+7];
	
					// x: center y: top
					T.m_a[i][j+5] = T.m_left.m_a[i+12][j+7];
					
					// x: left y:center
					T.m_a[i+5][j] = T.m_left.m_a[i+7][j+12];
	
					// x: center y:center
					T.m_a[i+5][j+5] = T.m_left.m_a[i+7][j+7];
	
					// x: bottom y: bottom
					T.m_a[i+10][j+10] = T.m_right.m_a[i+12][j+12];
	
					// x: left y:bottom
					T.m_a[i+10][j] = T.m_right.m_a[i+12][j+7];
	
					// x: right y: top
					T.m_a[i][j+10] = T.m_right.m_a[i+7][j+12];
	
				}
				T.m_b[i] = T.m_left.m_b[i+12] + T.m_right.m_b[i+7];
				T.m_b[i+5] = T.m_left.m_b[i+7];
				T.m_b[i+10] = T.m_right.m_b[i+12];
			}
		}
		if (T.m_left.m_a.length == T.m_right.m_a.length && T.m_left.m_a.length == 15) {
			for (i=0;i<5;i++) {
				for (j=0; j<5; j++) {
					// x:left y:top
					T.m_a[i][j] = T.m_left.m_a[i+10][j+10] + T.m_right.m_a[i+5][j+5];
	
					// x: center y: top
					T.m_a[i][j+5] = T.m_left.m_a[i+10][j+5];
					
					// x: left y:center
					T.m_a[i+5][j] = T.m_left.m_a[i+5][j+10];
	
					// x: center y:center
					T.m_a[i+5][j+5] = T.m_left.m_a[i+5][j+5];
	
					// x: bottom y: bottom
					T.m_a[i+10][j+10] = T.m_right.m_a[i+10][j+10];
	
					// x: left y:bottom
					T.m_a[i+10][j] = T.m_right.m_a[i+10][j+5];
	
					// x: right y: top
					T.m_a[i][j+10] = T.m_right.m_a[i+5][j+10];
	
				}
				T.m_b[i] = T.m_left.m_b[i+10] + T.m_right.m_b[i+5];
				T.m_b[i+5] = T.m_left.m_b[i+5];
				T.m_b[i+10] = T.m_right.m_b[i+10];
			}
		}
		System.out.println("==== A2 ====");
		MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}

}
