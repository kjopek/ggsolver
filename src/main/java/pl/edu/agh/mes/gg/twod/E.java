package pl.edu.agh.mes.gg.twod;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

// elimination
public class E extends Production {

	public E(Vertex Vert, Counter Count) {
		super(Vert, Count);
	}

	@Override
	public Vertex apply(Vertex T) {
		MatrixUtils.eliminate(5, T.m_a, T.m_b);
		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}

}
