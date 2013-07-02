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
		  
		  double [][] data = m_tier.getMatrix().clone();
		  double rhs[] = m_tier.getRhs().clone();
		  
		  for (int i=0; i<7; i++) {
			  for (int j=0; j<7; j++) {
				  data[(i+1)%8][(j+1)%8] = m_tier.getMatrix()[i][j]; 
			  }
			  rhs[(i+1)%8] = m_tier.getRhs()[i];
		  }
		  
		  MatrixUtils.eliminate(1, data, rhs);
		  
		  for (int i=1; i<6; i++) {
			  for (int j=1; j<6; j++) {
				  T.m_a[i-1][j-1] = data[i][j];
			  }
			  T.m_b[i-1] = rhs[i];
		  }
		  
		  return T;
	}
	
}
