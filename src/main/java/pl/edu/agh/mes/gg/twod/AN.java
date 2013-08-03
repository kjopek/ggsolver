package pl.edu.agh.mes.gg.twod;

import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class AN extends Production {
	public AN (Vertex Vert,Counter Count){
		super(Vert,Count);
	}

	public AN (Vertex Vert,Counter Count, Tier tier){
		super(Vert,Count,tier);
	}

	public Vertex apply(Vertex T) {

		double [][] tierMatrix = m_tier.getMatrix();
		double [] tierRhs = m_tier.getRhs();
		
		double [][] matrix = new double[21][21];
		double []rhs = new double[21];


		for (int i=0; i<17; i++) {
			for (int j=0; j<17; j++) {
				matrix[i+4][j+4] = tierMatrix[i][j]; 
			}
			rhs[i+4] = tierRhs[i];
		}

		for (int i=0;i<4;i++) {
			for (int j=0;j<17;j++) {
				matrix[i][j+4] = tierMatrix[i+17][j];
				matrix[j+4][i] = tierMatrix[j][i+17];
			}
		}

		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				matrix[i][j] = tierMatrix[i+17][j+17];
			}
			rhs[i] = tierRhs[i+17];
		}

		MatrixUtils.eliminate(4, matrix, rhs);

		T.orig_matrix = matrix;
		T.orig_rhs = rhs;
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i+7][j+7] = matrix[i+4][j+4]; // 1
				T.m_a[i+7][j+12] = matrix[i+4][j+12+4]; // 3
				T.m_a[i+12][j+7] = matrix[i+12+4][j+4]; // 7
				T.m_a[i+12][j+12] = matrix[i+12+4][j+12+4]; // 9
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				T.m_a[i][j] = matrix[i+5+4][j+5+4]; // 5
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i][j+7] = matrix[i+5+4][j+4]; // 4
				T.m_a[i][j+12] = matrix[i+5+4][j+12+4]; // 6
				T.m_a[j+7][i] = matrix[j+4][i+5+4]; // 2
				T.m_a[j+12][i] = matrix[j+12+4][i+5+4]; // 8
			}
		}
		
		for (int i=0; i<5; i++) {
			T.m_b[i+7] = rhs[i+4];
			T.m_b[i+12] = rhs[i+12+4];
		}
		
		for (int i=0; i<7; i++) {
			T.m_b[i] = rhs[i+5+4];
		}
		
		MatrixUtils.eliminate(7, T.m_a, T.m_b);
		return T;
	}

}
