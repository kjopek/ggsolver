package pl.edu.agh.mes.gg.twod;

import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class A1 extends Production{
	public A1 (Vertex Vert,Counter Count){
		super(Vert,Count);
	}

	public A1 (Vertex Vert,Counter Count, Tier tier){
		super(Vert,Count,tier);
	}

	public Vertex apply(Vertex T) {
		double [][] tierMatrix = m_tier.getMatrix();
		double []tierRhs = m_tier.getRhs();


		// in A1 we need to move 2,4,6,8 [row,col] to the top-left corner of matrix
		MatrixUtils.swapCols(0, 1, tierMatrix);
		MatrixUtils.swapCols(2, 3, tierMatrix);
		MatrixUtils.swapCols(4, 5, tierMatrix);
		MatrixUtils.swapCols(6, 7, tierMatrix);

		MatrixUtils.swapCols(1, 2, tierMatrix);
		MatrixUtils.swapCols(3, 4, tierMatrix);
		MatrixUtils.swapCols(5, 6, tierMatrix);

		MatrixUtils.swapCols(2, 3, tierMatrix);
		MatrixUtils.swapCols(4, 5, tierMatrix);

		MatrixUtils.swapCols(3, 4, tierMatrix);

		MatrixUtils.swapRows(0, 1, tierMatrix, tierRhs);
		MatrixUtils.swapRows(2, 3, tierMatrix, tierRhs);
		MatrixUtils.swapRows(4, 5, tierMatrix, tierRhs);
		MatrixUtils.swapRows(6, 7, tierMatrix, tierRhs);

		MatrixUtils.swapRows(1, 2, tierMatrix, tierRhs);
		MatrixUtils.swapRows(3, 4, tierMatrix, tierRhs);
		MatrixUtils.swapRows(5, 6, tierMatrix, tierRhs);

		MatrixUtils.swapRows(2, 3, tierMatrix, tierRhs);
		MatrixUtils.swapRows(4, 5, tierMatrix, tierRhs);

		MatrixUtils.swapRows(3, 4, tierMatrix, tierRhs);

		MatrixUtils.eliminate(4, tierMatrix, tierRhs);

		T.orig_matrix = tierMatrix;
		T.orig_rhs = tierRhs;
		
		System.out.println("==== A1 - pre processed ====");
		MatrixUtils.printMatrix(tierMatrix,  tierRhs);
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i+7][j+7] = tierMatrix[i+4][j+4]; // 1
				T.m_a[i+7][j+12] = tierMatrix[i+4][j+12+4]; // 3
				T.m_a[i+12][i+7] = tierMatrix[i+12+4][j+4]; // 7
				T.m_a[i+12][j+12] = tierMatrix[i+12+4][j+12+4]; // 9
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				T.m_a[i][j] = tierMatrix[i+5+4][j+5+4]; // 5
			}
		}

		for (int i=0; i<7; i++) {
			for (int j=0; j<5; j++) {
				T.m_a[i][j+7] = tierMatrix[i+5+4][j+4]; // 4
				T.m_a[i][j+12] = tierMatrix[i+5+4][j+12+4]; // 6
				T.m_a[j+7][i] = tierMatrix[j+4][i+5+4]; // 2
				T.m_a[j+12][i] = tierMatrix[j+12+4][i+5+4]; // 8
			}
		}
		
		for (int i=0; i<5; i++) {
			T.m_b[i+7] = tierRhs[i+4];
			T.m_b[i+12] = tierRhs[i+12+4];
		}
		
		for (int i=0; i<7; i++) {
			T.m_b[i] = tierRhs[i+5+4];
		}

		System.out.println("==== A1 - pre elimination ====");
		MatrixUtils.printMatrix(T.m_a, T.m_b);
		
		MatrixUtils.eliminate(7, T.m_a, T.m_b);

		// save the original matrix with all unknowns
		T.orig_matrix = tierMatrix;
		T.orig_rhs = tierRhs;
		System.out.println("==== A1 ====");
		MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}
}
