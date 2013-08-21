package pl.edu.agh.mes.gg.oned;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class ERoot extends Production{

	public ERoot(Vertex vertex, Counter counter){
		super(vertex,counter);
	}
	
	@Override
	public Vertex apply(Vertex T) {
		double x = T.m_a[0][0];
		T.m_a[0][0] /= x; 
		T.m_a[0][1] /= x;
		T.m_a[0][2] /= x;
		T.m_b[0] /= x;
		
		x = T.m_a[1][0];
		T.m_a[1][0] = 0; 
		T.m_a[1][1] -= x*T.m_a[0][1];
		T.m_a[1][2] -= x*T.m_a[0][2];
		T.m_b[1] -= x*T.m_b[0];
		
		x = T.m_a[2][0];
		T.m_a[2][0] = 0; 
		T.m_a[2][1] -= x*T.m_a[0][1];
		T.m_a[2][2] -= x*T.m_a[0][2];
		T.m_b[2] -= x*T.m_b[0];
		
		x = T.m_a[1][1];
		T.m_a[1][1] /=x;
		T.m_a[1][2] /=x;
		T.m_b[1] /=x; 
		
		x = T.m_a[2][1];
		T.m_a[2][1] = 0; 
		T.m_a[2][2] -= x*T.m_a[1][2]; 
		T.m_b[2] -=x*T.m_b[1];
		
		x = T.m_a[2][2];
		T.m_a[2][2] /= x;
		T.m_b[2] /= x; 
		
		return T;
	}
	
}