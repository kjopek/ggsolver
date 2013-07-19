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
		  
		  T.m_a = new double[6][6];
		  T.m_b = new double[6];
		  
		  double [][] data = new double[7][7];
		  double [] rhs = new double[7];

		  for (int i=0; i<6; i++) {
			  for (int j=0; j<6; j++) {
				  data[i+1][j+1] = m_tier.getMatrix()[i][j]; 
			  }
			  rhs[i+1] = m_tier.getRhs()[i];
		  }
		  
		  for (int i=0;i<6;i++) {
			  data[0][i+1] = m_tier.getMatrix()[6][i];
			  data[i+1][0] = m_tier.getMatrix()[i][6];
		  }
		  data[0][0] = m_tier.getMatrix()[6][6];
		  rhs[0] = m_tier.getRhs()[6];
		  
		  MatrixUtils.eliminate(1, data, rhs);		  
		  
		  // copy out matrix
		  for (int i=1; i<7; i++) {
			  for (int j=1; j<7; j++) {
				  T.m_a[i-1][j-1] = data[i][j];
			  }
			  T.m_b[i-1] = rhs[i];
		  }
		  
		  T.orig_matrix = data;
		  T.orig_rhs = rhs;
		  
		  		  
		  return T;
	}
	
}
