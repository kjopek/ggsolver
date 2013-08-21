package pl.edu.agh.mes.gg.twod;

import pl.edu.agh.mes.gg.Counter;
import pl.edu.agh.mes.gg.MatrixUtils;
import pl.edu.agh.mes.gg.Production;
import pl.edu.agh.mes.gg.Vertex;

public class ERoot extends Production {

	public ERoot(Vertex Vert, Counter Count) {
		super(Vert, Count);
	}

	@Override
	public Vertex apply(Vertex T) {
		// final elimination
		MatrixUtils.eliminate(15, T.m_a, T.m_b);
		return T;
	}

}
