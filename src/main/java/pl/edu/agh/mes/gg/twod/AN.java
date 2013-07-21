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
		  System.out.println("AN");
		  
		  double [][] matrix = new double[21][21];
		  double []rhs = new double[21];

		  final int size = 17;
		  
		  T.m_a = new double[size][size];
		  T.m_b = new double[size];		  
		  
		  for (int i=0; i<17; i++) {
			  for (int j=0; j<17; j++) {
				  matrix[i+4][j+4] = m_tier.getMatrix()[i][j]; 
			  }
			  rhs[i+4] = m_tier.getRhs()[i];
		  }

		  for (int i=0;i<4;i++) {
			  for (int j=0;j<17;j++) {
				  matrix[i][j+4] = m_tier.getMatrix()[i+17][j+4];
				  matrix[j+4][i] = m_tier.getMatrix()[j][i+17];
			  }
		  }
		  
		  for (int i=0; i<4; i++) {
			  for (int j=0; j<4; j++) {
				  matrix[i][j] = m_tier.getMatrix()[i+17][j+17];
			  }
		  }
		  
		  MatrixUtils.eliminate(4, matrix, rhs);
		    
		  // copy out matrix
		  for (int i=0; i<17; i++) {
			  for (int j=0; j<17; j++) {
				  T.m_a[i][j] = matrix[i+4][j+4];
			  }
			  T.m_b[i] = rhs[i+4];
		  }
		  
		  T.orig_matrix = matrix;
		  T.orig_rhs = rhs;
		  
		  return T;
	}
	
}
