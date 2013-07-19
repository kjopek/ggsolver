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

		  T.m_a = new double[6][6];
		  T.m_b = new double[6];
		  
		  // XXX: do we really need this?
		  double [][] matrix = m_tier.getMatrix().clone();
		  double []rhs = m_tier.getRhs().clone();
		  
		  // in A1 we need to move second and fourth [row,col] to the top-left corner of matrix
		  MatrixUtils.swapCols(0, 1, matrix);
		  MatrixUtils.swapCols(2, 3, matrix);
		  MatrixUtils.swapCols(1, 2, matrix);
		  MatrixUtils.swapRows(0, 1, matrix, rhs);
		  MatrixUtils.swapRows(2, 3, matrix, rhs);
		  MatrixUtils.swapRows(1, 2, matrix, rhs);

		  // pre-processing
		  MatrixUtils.eliminate(5, matrix, rhs);
		  
		  T.orig_matrix = matrix;
		  T.orig_rhs = rhs;
		  
		  // copy out eliminated matrix
		  for (int i=0; i<6; i++) {
			  for (int j=0;j<6;j++) {
				  T.m_a[i][j] = matrix[i+2][j+2];
			  }
			  T.m_b[i] = rhs[i+2];
		  }		  
		  
		  return T;
	}
}
