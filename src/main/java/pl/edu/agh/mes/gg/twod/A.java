package pl.edu.agh.mes.gg.twod;

import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class A extends Production {
	public A (Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	public A (Vertex Vert,Counter Count, Tier tier){
		super(Vert,Count,tier);
		
	}
	public Vertex apply(Vertex T) {

		double [][] tierMatrix = m_tier.getMatrix();
		double [] tierRhs = m_tier.getRhs();
		
		System.out.println("==== A - pre processed ====");
		MatrixUtils.printMatrix(tierMatrix, tierRhs);
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i+7][j+7] = tierMatrix[i][j]; // 1
				T.m_a[i+7][j+12] = tierMatrix[i][j+12]; // 3
				T.m_a[i+12][i+7] = tierMatrix[i+12][j]; // 7
				T.m_a[i+12][j+12] = tierMatrix[i+12][j+12]; // 9
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				T.m_a[i][j] = tierMatrix[i+5][j+5]; // 5
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i][j+7] = tierMatrix[i+5][j]; // 4
				T.m_a[i][j+12] = tierMatrix[i+5][j+12]; // 6
				T.m_a[j+7][i] = tierMatrix[j][i+5]; // 2
				T.m_a[j+12][i] = tierMatrix[j+12][i+5]; // 8
			}
		}
		
		for (int i=0; i<5; i++) {
			T.m_b[i+7] = tierRhs[i];
			T.m_b[i+12] = tierRhs[i+12];
		}
		
		for (int i=0; i<7; i++) {
			T.m_b[i] = tierRhs[i+5];
		}
		
		MatrixUtils.eliminate(7, T.m_a, T.m_b);
		System.out.println("======");
		MatrixUtils.printMatrix(T.m_a, T.m_b);

		return T;

	}
}
