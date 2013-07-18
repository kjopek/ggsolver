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
		  System.out.println("A1");
		  
		  // XXX: do we really need this?
		  double [][] matrix = m_tier.getMatrix().clone();
		  double []rhs = m_tier.getRhs().clone();
		  
		  // in A1 we need to move 2,4,6,8 [row,col] to the top-left corner of matrix
		  MatrixUtils.swapCols(0, 1, matrix);
		  MatrixUtils.swapCols(2, 3, matrix);
		  MatrixUtils.swapCols(4, 5, matrix);
		  MatrixUtils.swapCols(6, 7, matrix);
		  
		  MatrixUtils.swapCols(1, 2, matrix);
		  MatrixUtils.swapCols(3, 4, matrix);
		  MatrixUtils.swapCols(5, 6, matrix);

		  MatrixUtils.swapCols(2, 3, matrix);
		  MatrixUtils.swapCols(4, 5, matrix);
		  
		  MatrixUtils.swapCols(3, 4, matrix);

		  MatrixUtils.swapRows(0, 1, matrix, rhs);
		  MatrixUtils.swapRows(2, 3, matrix, rhs);
		  MatrixUtils.swapRows(4, 5, matrix, rhs);
		  MatrixUtils.swapRows(6, 7, matrix, rhs);
		  
		  MatrixUtils.swapRows(1, 2, matrix, rhs);
		  MatrixUtils.swapRows(3, 4, matrix, rhs);
		  MatrixUtils.swapRows(5, 6, matrix, rhs);

		  MatrixUtils.swapRows(2, 3, matrix, rhs);
		  MatrixUtils.swapRows(4, 5, matrix, rhs);
		  
		  MatrixUtils.swapRows(3, 4, matrix, rhs);

		  

		  // pre-processing
		  MatrixUtils.eliminate(5, matrix, rhs);
		  System.out.println("========");
		  MatrixUtils.printMatrix(matrix, rhs);
		  
		  // copy out eliminated matrix
		  for (int i=0; i<17; i++) {
			  for (int j=0;j<17;j++) {
				  T.m_a[i][j] = matrix[i+4][j+4];
			  }
			  T.m_b[i] = rhs[i+4];
		  }		  
		  
		  return T;
	}
}
