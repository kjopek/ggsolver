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
		int offsetAl=0, offsetBl=0, offsetAr=0, offsetBr=0;
		
		T.m_a = new double[size][size];
		T.m_b = new double[size];
		
		
		if (T.m_left.m_a.length == 17 && T.m_right.m_a.length == 17)
		{
			offsetAl = 12;
			offsetBl = 0;
			offsetAr = 12;
			offsetBr = 0;
		} else if (T.m_left.m_a.length == 15 && T.m_right.m_a.length == 15) {
			offsetAl = 10;
			offsetBl = 5;
			offsetAr = 10;
			offsetBr = 5;
		} else if (T.m_left.m_a.length == 17 && T.m_right.m_a.length == 15) {
			offsetAl = 12;
			offsetBl = 0;
			offsetAr = 10;
			offsetBr = 5;
		} else if (T.m_left.m_a.length == 15 && T.m_right.m_a.length == 17) {
			offsetAl = 10;
			offsetBl = 5;
			offsetAr = 12;
			offsetBr = 0;
		} else {
			System.out.println("Error!");
		}
				
		for (i=0;i<5;i++) {
			for (j=0; j<5; j++) {
				// x:left y:top
				T.m_a[i][j] = T.m_left.m_a[i+offsetAl][j+offsetAl] + T.m_right.m_a[i+offsetBr][j+offsetBr];

				// x: center y: top
				T.m_a[i][j+5] = T.m_left.m_a[i+offsetAl][j+offsetBl];
				
				// x: left y:center
				T.m_a[i+5][j] = T.m_left.m_a[i+offsetBl][j+offsetAl];
				
				// x: center y:center
				T.m_a[i+5][j+5] = T.m_left.m_a[i+offsetBl][j+offsetBl];
				
				// x: bottom y: bottom
				T.m_a[i+10][j+10] = T.m_right.m_a[i+offsetAr][j+offsetAr];

				// x: left y:bottom
				T.m_a[i+10][j] = T.m_right.m_a[i+offsetAr][j+offsetBr];
				
				// x: right y: top
				T.m_a[i][j+10] = T.m_right.m_a[i+offsetBr][j+offsetAr]; 
				
			}
			T.m_b[i] = T.m_left.m_b[i+offsetAl] + T.m_right.m_b[i+offsetBr];
			T.m_b[i+5] = T.m_left.m_b[i+offsetBl];
			T.m_b[i+10] = T.m_right.m_b[i+offsetAr];
		}

		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}

}
