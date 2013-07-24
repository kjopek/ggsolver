package pl.edu.agh.mes.gg;


public class P2 extends Production {
	public P2(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	public Vertex apply(Vertex T) {
	  Vertex T1 = new Vertex(null,null,T,"T");
	  Vertex T2 = new Vertex(null,null,T,"T");

	  T.set_left(T1);

	  T.set_right(T2);

	  T.set_label("int");
	  return T;
	}
}