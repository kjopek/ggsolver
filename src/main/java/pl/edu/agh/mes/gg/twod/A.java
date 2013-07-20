package pl.edu.agh.mes.gg.twod;

import matrixgeneration.Tier;
import pl.edu.agh.mes.gg.Counter;
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
	  System.out.println("A");
	  final int size = 17;
	  
	  T.m_a = new double[size][size];
	  T.m_b = new double[size];
	  
	  for (int i=0; i<17;i++) {
		  for (int j=0;j<17;j++) {
			  T.m_a[i][j] = m_tier.getMatrix()[i][j];
		  }
		  T.m_b[i] = m_tier.getRhs()[i];
	  }
	  return T;
	}
}
