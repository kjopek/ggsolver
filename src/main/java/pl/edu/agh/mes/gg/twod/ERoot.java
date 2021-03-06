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
		System.out.println("ERoot");
		// final elimination
		MatrixUtils.eliminate(9, T.m_a, T.m_b);
		//MatrixUtils.printMatrix(T.m_a, T.m_b);
		return T;
	}

}
