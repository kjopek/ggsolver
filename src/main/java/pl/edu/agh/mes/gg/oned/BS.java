package pl.edu.agh.mes.gg.oned;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class BS extends Production{

	public BS(Vertex vertex, Counter counter){
		super(vertex, counter);
	}
	
	@Override
	public Vertex apply(Vertex T) {
		System.out.println("BS");
		if(T.m_parent == null){
			T.m_b[1] -= T.m_a[1][2]*T.m_b[2];
			T.m_b[0] -= T.m_a[0][2]*T.m_b[2] + T.m_a[0][1]*T.m_b[1];
		}
		else{
			Vertex parent = T.m_parent;
			if(T.m_parent.m_left == T){
				T.m_b[2] = parent.m_b[0];
				T.m_b[1] = parent.m_b[1];
				T.m_b[0] -= T.m_a[0][1]*T.m_b[1] + T.m_a[0][2]*T.m_b[2]; 
			}
			else{
				T.m_b[2] = parent.m_b[2];
				T.m_b[1] = parent.m_b[0];
				T.m_b[0] -= T.m_a[0][1]*T.m_b[1] + T.m_a[0][2]*T.m_b[2];
			}
			
		}
		
		return T; 
	}
	
}
