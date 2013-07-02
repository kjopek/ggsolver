package pl.edu.agh.mes.gg.oned;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class AN extends Production{

	public AN(Vertex vertex, Counter counter){
		super(vertex, counter);
	}
	
	@Override
	public Vertex apply(Vertex T) {
		System.out.println("AN");
		T.m_a[1][1]=-1.0;
		T.m_a[2][1]=0.0;
		T.m_a[1][2]=1.0;
		T.m_a[2][2]=1.0;
		T.m_b[1]=0.0;
		T.m_b[2]=21.0;
		
		
		return T; 
	}
	
}